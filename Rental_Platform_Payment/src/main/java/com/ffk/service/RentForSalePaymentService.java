package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/3/13 9:39
 */
@Configuration
@EnableScheduling //开启定时任务  处理支付
public class RentForSalePaymentService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IDepositService depositService;

    //日志
    Logger logger = LoggerFactory.getLogger(RentForSalePaymentService.class);

    /**
     *  每10s执行一次  这个方法处理以租代售订单
     * @param
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    @GlobalTransactional
    public void paymentInRentForSale() throws Exception {
        //遍历订单表 查询到已经到达还款时间的且订单尚在进行中的  查看期数是否满 若满则修改订单状态为完成
        HashMap<Object, Object> conditionHashMap = new HashMap<>();
        //订单状态为进行中的
        conditionHashMap.put("status",Constants.ORDER_ONGOING);
        //且是以租代售订单
        conditionHashMap.put("orderType",Constants.RENT_FOR_SALE);
        //获取系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        conditionHashMap.put("payTime",time);
        System.out.println("查询条件:"+conditionHashMap);
        CommonResult commonResult = orderService.queryOrder(conditionHashMap);
        String s = JSON.toJSONString(commonResult.getData());
        List<Order> orders = JSONArray.parseArray(s, Order.class);
        logger.info("扣款订单:{}",orders.toString());
        if(orders!=null){
            //遍历满足条件还有钱未支付的订单
            for (Order order : orders) {
                //订单Id
                int orderId = order.getOrderId();
                logger.info("订单ID:{}",orderId);
                //得到订单总金额
                int sumMoney = order.getSumMoney();
                logger.info("订单总金额:{}",sumMoney);
                //得到总期数
                int totalTime = order.getTotalTime();
                logger.info("总期数:{}",totalTime);
                //得到每期应支付多少钱
                int perTimeMoney = sumMoney/totalTime;
                logger.info("每期应支付:{}",perTimeMoney);
                //用户合约地址(该扣谁的钱)
                String userContractAddress =  "";
                HashMap<Object, Object> userCondition = new HashMap<>();
                CommonResult userList = userService.queryUser(userCondition);
                //拿到所有用户
                List<Users> users = JSON.parseArray(JSON.toJSONString(userList.getData()), Users.class);
                //当前该笔订单属于哪个用户
                Users userOrder = null;
                //遍历用户
                for (Users user : users) {
                    //拿到用户的合约地址
                    String contractAddress = user.getContractAddress();
                    UserContract userContract = UserContractService.load(contractAddress);
                    //查询
                    List<BigInteger> allOrdersId = UserContractService.getAllOrdersId(userContract);
                    if(allOrdersId.contains(BigInteger.valueOf(orderId))){
                        //是该用户的订单
                        userContractAddress = user.getContractAddress();
                        userOrder = user;
                        break;
                    }
                }
                //根据商家id查询商家的合约地址
                int manufacturerId = order.getManufacturerId();
                //调用商家服务
                CommonResult manufacturerResult = manufacturerService.queryManufacturerById(manufacturerId);
                //转商家对象
                Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Manufacturer.class);
                //获取商家地址
                String manufacturerAddress = manufacturer.getContractAddress();
                //加载用户合约
                UserContract userContract = UserContractService.load(userContractAddress);
                //支付 需判断余额够不够
                try {
                    //付租金
                    boolean rs = UserContractService.transfer(manufacturerAddress, BigInteger.valueOf(perTimeMoney),userContract);
                    //获取订单总金额
                    int orderSumMoney = order.getSumMoney();
                    //获取当前期数
                    int currTime = order.getCurrTime();
                    //已支付金额
                    int actualPay = order.getActualPay();
                    //判断支付是否成功 rs为true则成功 rs为false则失败
                    if(rs){
                        //本次付款成功 写入商家收益
                        ManufacturerProfit manufacturerProfit = new ManufacturerProfit();
                        //设置收益的金额
                        manufacturerProfit.setAmount(perTimeMoney);
                        //查询订单对应的商品nftID
                        int nftIdByOrderId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
                        //调用商品服务
                        CommonResult nftResult = commodityService.queryNFTByNftId(nftIdByOrderId);
                        //得到商品NFT对象
                        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);
                        //设置商品利润来源
                        manufacturerProfit.setCommodityId(nftCommodity.getGoodsId());
                        String profitTime = df.format(System.currentTimeMillis());
                        //收益的时间
                        manufacturerProfit.setProfitTime(profitTime);
                        //该笔利润属于哪个商家
                        manufacturerProfit.setManufacturerId(order.getManufacturerId());
                        //调用商家服务
                        manufacturerService.addProfit(manufacturerProfit);
                        //已付清
                        if((actualPay+perTimeMoney) == orderSumMoney){
                            logger.info("已付清，即将更改订单状态为已完成");
                            //更新订单状态
                            order.setStatus(Constants.ORDER_FINISH);
                            //更新实际已支付
                            order.setActualPay(orderSumMoney);
                            //更新当前期数
                            order.setCurrTime(currTime+1);
                            //更新下一期支付时间 标志为不用支付了
                            order.setNextPay(Constants.ORDER_END_NEXTPAY);
                            //订单完成时间
                            String finishTime = df.format(System.currentTimeMillis());
                            //更新订单完成时间
                            order.setFinishTime(finishTime);
                            //更新商品NFT状态
                            nftCommodity.setStatus(Constants.NFT_SELLED);
                            //调用商品服务更新商品NFT
                            commodityService.updateNFTCommodity(nftCommodity);
                            //更新订单状态
                            orderService.updateOrder(order);
                            //退回押金 改变NFT所有权
                            HashMap<Object, Object> conditionMap = new HashMap<>();
                            //害 这里api没设计好
                            conditionMap.put("orderId",orderId);
                            //调用押金服务退押金
                            depositService.backDeposit(conditionMap);
                            //加载商品NFT合约
                            CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
                            //钱已经付清了 改变商品NFT的所有权
                            CommodityContractService.transferOwnerShip(manufacturerAddress,userContractAddress,nftIdByOrderId,commodityNFTContract);
                        }else {   //未付清
                            //当前期数+1
                            order.setCurrTime(currTime+1);
                            //设置实际已支付了的钱
                            order.setActualPay(actualPay+perTimeMoney);
                            //调用订单服务更新订单
                            orderService.updateOrder(order);
                        }
                    }else {
                            //余额不够
                            logger.info("您账户的余额不足以支付分期余额");
                            //标识不再拥有租赁权
                            userOrder.setRentQualification(Constants.NO_HAS_RENT_QULIFICATION);
                            logger.info("该用户将不再拥有租赁权:{}",userOrder);
                            //调用用户服务
                            userService.updateUser(userOrder);
                            //更新订单为黑名单订单（欠费订单，只针对以租代售）
                            order.setStatus(Constants.BLACK_USER_ORDER);
                            //扣除信用积分 这里可能会一直扣分 流程有待优化 只扣一分是不是太少了？
                            UserContractService.decreaseCredit(userContract);
                            //调用订单服务更新订单
                            orderService.updateOrder(order);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            logger.info("当前没有满足条件的订单!");
        }
    }
}
