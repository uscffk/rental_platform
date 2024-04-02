package com.ffk.service;
import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import com.ffk.utils.TestBaiDuMap;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/6 17:08
 */
//还可能要考虑商品新的位置信息 更新redis和数据库 用户扫码 携带用户ID和NFT ID向后端发请求 再决定跳转到哪个页面 跳转到租赁页面还是可退租页面 如果该nft处于空闲状态则跳到租赁页面 不然
@Service
public class IBackCommodityServiceImpl implements IBackCommodityService{

    @Autowired
    private IStockService stockService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IPlatformService platformService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private TestBaiDuMap baiDuMap;

    @Autowired
    private IDepositService depositService;

    Logger logger = LoggerFactory.getLogger(IBackCommodityServiceImpl.class);

    private String backDetailLoc;

    /**
     * 佛系操作 judgeIsInBackLocation函数找到了一个可以还的站点就应该返回位置的 我懒得改了
     * @param detailLoc
     */
    private synchronized void setBackDetailLoc(String detailLoc){
        this.backDetailLoc = detailLoc;
    }

    private String getBackDetailLoc(){
        return this.backDetailLoc;
    }

    /**
     * 判断是否在退租范围内
     * @param longitude   用户当前位置的经度
     * @param latitude    用户当前位置的纬度
     * @param nftId
     * @return
     */
    @Override
    public boolean judgeIsInBackLocation(String longitude, String latitude, int nftId) {
        //初始化退租结果为false 即不在退租范围内
        boolean flag = false;
        CommonResult nftCommonResult = commodityService.queryNFTByNftId(nftId);
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftCommonResult.getData()), NFTCommodity.class);
        logger.info("nftCommodity:{}",nftCommodity);
        int goodsId = nftCommodity.getGoodsId();
        //根据商品id查询站点
        CommonResult stationCommonResult = commodityService.queryStationBygoodsId(goodsId);
        //转换成所有站点对象
        List<BackLocation> backLocations = JSON.parseArray(JSON.toJSONString(stationCommonResult.getData()), BackLocation.class);
        logger.info("站点:{}",backLocations);
        //用户的当前位置
        HashMap<String, String> locationA = new HashMap<>(2);
        locationA.put("lng",longitude);
        locationA.put("lat",latitude);
        //开始遍历 站点
        for (BackLocation backLocation : backLocations) {
            String longitudeB = backLocation.getLongitude();
            String latitudeB = backLocation.getLatitude();
            HashMap<String, String> locationB = new HashMap<>(2);
            locationB.put("lng",longitudeB);
            locationB.put("lat",latitudeB);
            //算距离
            double distance = baiDuMap.distance(locationA, locationB);
            logger.info("两地距离:{}",distance);
            if(distance>Constants.BACKRENT_RADIUS){
                //不在退租范围内 继续判断下一个点
                continue;
            }else {
                //在退租范围内
                this.setBackDetailLoc(backLocation.getDetailLoc());
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 退租
     * @param  orderId 订单id
     * return 0:不再退租范围之内 1:余额不足 2:成功
     */
    @Override
    @GlobalTransactional
    public int backCommodity(int orderId,String longitude,String latitude,int nftId) throws Exception {
        //拿到商品信息
        CommonResult nftResult = commodityService.queryNFTByNftId(nftId);
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);

        logger.info("要退租的商品:{}",nftCommodity.toString());
        logger.info("经度:{} 纬度:{}",longitude,latitude);
        //查询位置
        Commodity commodity = nftCommodity.getCommodity();

        boolean rs = this.judgeIsInBackLocation(longitude, latitude, nftId);

        //如果不在退租范围内
        if(!rs){
            logger.info("不在退租范围之内");
            return 0;
        }

        logger.info("在退租范围之内");
        //调用支付服务
        CommonResult payment = paymentService.payment(orderId);
        //拿到订单
        CommonResult orderResult = orderService.queryById(orderId);
        Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
        //商家
        CommonResult manufacturerResult = manufacturerService.queryManufacturerById(order.getManufacturerId());
        Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Manufacturer.class);
        //找到该笔订单属于谁
        CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
        //找到nft的使用权主人
        String ownerofUseright = CommodityContractService.ownerofUseright(nftId, commodityNFTContract);
        //支付结果 这里会有点问题 如果支付服务崩了 这边就不执行了 但是支付那边已经动了合约了 数据不一致
        //其实项目还有一个很大的问题 微服务架构进行了分库操作 用seata解决分布式事务 but 如何解决链上链下的事务问题？ 这里我初步想法是把一些调用合约的方法放在一个事务的最后面执行 某些情况是适用的
        //完美的解决方案甚至要写一个中间件框架（Seata协调了不同的库，写一个中间件协调以太坊和数据库，想想就好了吧）  更有甚者因为以太坊的智能合约的执行机制这个问题根本无法解决
        String data = (String) payment.getData();
        if(data.equals("转账成功")){
            //支付成功  库存 NFT状态(status 经度 纬度) NFT使用权  退押金 加信用
            logger.info("转账成功");
            //改变NFT的使用权
            CommodityContractService.transferUseRight(ownerofUseright,manufacturer.getContractAddress(),nftId,commodityNFTContract);
            //改变NFT的状态
            nftCommodity.setStatus(Constants.NFT_FREE);
            nftCommodity.setLongitude(longitude);
            nftCommodity.setLatitude(latitude);
            nftCommodity.setDetailLoc(this.getBackDetailLoc());
            //更新NFT
            commodityService.updateNFTCommodity(nftCommodity);
            //加信用
            UserContract userContract = UserContractService.load(ownerofUseright);
            //订单的金额
            int sumMoney = order.getSumMoney();
            //订单总金额
            logger.info("订单总金额:{}",sumMoney);
            //加信用
            UserContractService.increaseCredit(userContract,BigInteger.valueOf(sumMoney));
            //加库存
            stockService.addStock(commodity.getId(),1);
            Map<String,Integer> map = new HashMap<>();
            map.put("orderId",orderId);
            //调用押金服务
            depositService.backDeposit(map);
            return 2;
        }else {
            //转账失败 退租失败
            return 1;
        }
    }

}
