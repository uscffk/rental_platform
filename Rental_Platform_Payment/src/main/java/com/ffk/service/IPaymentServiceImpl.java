package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 房发科
 * @create 2022/3/4
 */
@Service
public class IPaymentServiceImpl implements IPaymentService{

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IManufacturerService manufacturerService;

    Logger logger = LoggerFactory.getLogger(IPaymentServiceImpl.class);

    /**
     * 这个方法处理的是共享租赁的钱  先租后买购买的钱请回到rentServiceImpl里面去找吧 这里我的锅
     * 这里我后面打算用设计模式去重构
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    @GlobalTransactional
    public String payment(int orderId) throws Exception {
        logger.info("订单Id:{}", orderId);
        //查扣谁的钱
        String userContractAddress =  "";
        HashMap<Object, Object> userCondition = new HashMap<>();
        CommonResult userList = userService.queryUser(userCondition);
        List<Users> users = JSON.parseArray(JSON.toJSONString(userList.getData()), Users.class);
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
                break;
            }
        }
        //准备扣钱  计算花了多少钱
        CommonResult orderResult = orderService.queryById(orderId);
        //转对象
        logger.info("订单:{}",orderResult);
        Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
        logger.info("扣钱的订单:{}",order);
        logger.info("扣谁的钱:{}",userContractAddress);
        //得到订单开始的时间
        String creatTime = order.getCreatTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(creatTime);
        //订单开始的时间戳
        long time = date.getTime();
        //现在的时间戳
        long now = df.parse(df.format(new Date())).getTime();
        //判断是按月还是按日还是按小时
        int billMethod = order.getBillMethod();
        //期数
        int times = 0;
        if (billMethod== Constants.HOUR_RENT){ //按小时算钱
            times = ((int) Math.ceil((now - time)/1000 / (60 * 60)))==0?1:(int) Math.ceil((now - time)/1000 / (60 * 60));

        }else if(billMethod==Constants.DAY_RENT){ //按天算钱
            times = ((int) Math.ceil((now - time)/1000 / (60 * 60 * 24)))==0?1:(int) Math.ceil((now - time)/1000 / (60 * 60 * 24));

        }else {  //按月
            times = ((int) Math.ceil((now - time)/1000 / ((60 * 60 * 24)*30)))==0?1:(int) Math.ceil((now - time)/1000 / ((60 * 60 * 24)*30));
        }

        //通过订单Id获取NFT ID
        UserContract userContract = UserContractService.load(userContractAddress);
        int nftId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId),userContract);
        //调用商品服务
        CommonResult commodityResult = commodityService.queryNFTByNftId(nftId);
        //转对象
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(commodityResult.getData()), NFTCommodity.class);
        Commodity commodity = nftCommodity.getCommodity();
        //得到每期价格
        int rentPrice = commodity.getRentPrice();

        //应该支付的钱
        int money = rentPrice * times;

        //查询余额
        BigInteger balance = UserContractService.getBalance(userContract);

        if(balance.intValue()<money){
            return "余额不足请充值!";
        }

        //付款
        int manufacturerId = order.getManufacturerId();
        CommonResult manufacturerResult = manufacturerService.queryManufacturerById(manufacturerId);
        Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Manufacturer.class);
        //商家合约地址
        String contractAddress = manufacturer.getContractAddress();
        //转账
        boolean transfer = UserContractService.transfer(contractAddress, BigInteger.valueOf(money), userContract);

        if(transfer){
            //更新订单 //更新订单的状态 完成时间 总金额 actualPay nextPay
            order.setStatus(Constants.ORDER_FINISH);
            order.setActualPay(money);
            String finishTime = df.format(System.currentTimeMillis());
            order.setFinishTime(finishTime);
            order.setSumMoney(money);
            //订单服务
            orderService.updateOrder(order);
            //写进收益
            ManufacturerProfit manufacturerProfit = new ManufacturerProfit();
            manufacturerProfit.setAmount(money);
            manufacturerProfit.setCommodityId(nftCommodity.getGoodsId());
            manufacturerProfit.setProfitTime(finishTime);
            manufacturerProfit.setManufacturerId(order.getManufacturerId());
            //调用商家服务
            manufacturerService.addProfit(manufacturerProfit);
            return "转账成功";
        }
        return "以太坊转账出错";
    }
}
