package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;

import com.ffk.contract.PlatformContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.ManufacturerContractService;
import com.ffk.contract.service.PlatformContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.dao.IPlatform;
import com.ffk.pojo.*;
import com.ffk.vo.PlatformInfo;
import jnr.ffi.annotations.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/10 21:02
 */
@Service
public class IPlatformImpl implements IPlatformService,IPlatformInfo{
    @Autowired
    private IPlatform platform;

    @Autowired
    private IRechargeService rechargeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProfitService profitService;

    @Override
    public Platform queryPlatform(String username) {
        return platform.queryPlatform(username);
    }

    Logger logger = LoggerFactory.getLogger(IPlatformImpl.class);

    @Override
    public boolean transferToExteneralAccount() throws Exception {
        PlatformContract platformContract = PlatformContractService.load(Constants.PLATFORM_CONTRACT_ADDRESS);
        //注意这是平台的外部账户地址不是平台的合约地址  不是这个Constants.PLATFORM_CONTRACT_ADDRESS
        return PlatformContractService.transferToAccount(Constants.PLATFORM_COUNT_ADDRESS,platformContract);
    }

    @Override
    public boolean rechargeToSelf(int amount) throws Exception {
        //加载平台合约
        PlatformContract platformContract = PlatformContractService.load(Constants.PLATFORM_CONTRACT_ADDRESS);

        boolean rs = PlatformContractService.addMoney(BigInteger.valueOf(amount), platformContract);
        return rs;
    }

    /**
     * 充值 佛了 这里我当初脑袋不太清醒啊 用什么username 算了 直接摆烂
     * @param id  用户Id
     * @param amount 充值金额
     * @throws IOException
     * @throws CipherException
     */
    @Override
    public boolean recharge(int id, int amount,int from) throws Exception {
        logger.info("id:{}--amount:{}--form:{}",id,amount,from);
        //加载平台合约
        PlatformContract platFormContract = PlatformContractService.load(Constants.PLATFORM_CONTRACT_ADDRESS);
        boolean transfer = false;
        //插入记录
        Recharge rechar = new Recharge();
        logger.info("{}",Constants.RECHARGE_BY_USER);
        //0代表来源用户
        if(from == Constants.RECHARGE_BY_USER){
            logger.info("用户充值");
            CommonResult userCommonResult = userService.queryById(id);
            Users user = JSON.parseObject(JSON.toJSONString(userCommonResult.getData()), Users.class);
            String usersContractAddress = user.getContractAddress();
            //平台给用户转以太币
            transfer = PlatformContractService.recharge(usersContractAddress, BigInteger.valueOf(amount), platFormContract);
        }else if(from == Constants.RECHARGE_BY_MANU){ //来自商家充值
            logger.info("商家充值");
            CommonResult manuCommonResult = manufacturerService.queryManufacturerById(id);
            Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manuCommonResult.getData()), Manufacturer.class);
            String manuContractAddress = manufacturer.getContractAddress();
            transfer = PlatformContractService.recharge(manuContractAddress, BigInteger.valueOf(amount), platFormContract);
        }

        logger.info("充值结果:{}",transfer);

        if (transfer){ //充值成功
            rechar.setAmount(amount);
            rechar.setFromId(id);
            rechar.setIdentity(from);
            rechar.setType(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.ORDER_END_NEXTPAY);
            String time = simpleDateFormat.format(System.currentTimeMillis());
            rechar.setTime(time);
            rechargeService.addRecharge(rechar);
        }
        return transfer;

    }

    /**
     *  提现
     * @param id 用户名
     * @param amount 提现金额
     * @param from 来源
     */
    @Override
    public boolean getRMB(int id, int amount,int from) throws Exception {
        logger.info("id:{}--amount:{}--form:{}",id,amount,from);
        //加载平台合约
        PlatformContract platFormContract = PlatformContractService.load(Constants.PLATFORM_CONTRACT_ADDRESS);

        boolean transfer = false;
        //插入记录
        Recharge rechar = new Recharge();

        //0代表来源用户
        if(from == Constants.RECHARGE_BY_USER){
            logger.info("用户提现");
            CommonResult userCommonResult = userService.queryById(id);
            Users user = JSON.parseObject(JSON.toJSONString(userCommonResult.getData()), Users.class);
            String usersContractAddress = user.getContractAddress();
            //平台给用户转以太币
            transfer = UserContractService.load(usersContractAddress).transfer(Constants.PLATFORM_CONTRACT_ADDRESS, BigInteger.valueOf(amount)).send().isStatusOK();
        }else if(from == Constants.RECHARGE_BY_MANU){ //来自商家充值
            logger.info("商家提现");
            CommonResult manuCommonResult = manufacturerService.queryManufacturerById(id);
            Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manuCommonResult.getData()), Manufacturer.class);
            String manuContractAddress = manufacturer.getContractAddress();
            transfer = ManufacturerContractService.load(manuContractAddress).getRMB(BigInteger.valueOf(amount),Constants.PLATFORM_CONTRACT_ADDRESS).send().isStatusOK();
        }

        if(transfer){
            //提现成功
            //插入记录
            rechar.setType(1);
            rechar.setAmount(amount);
            rechar.setFromId(id);
            rechar.setIdentity(from);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.ORDER_END_NEXTPAY);
            String time = simpleDateFormat.format(System.currentTimeMillis());
            rechar.setTime(time);
            rechargeService.addRecharge(rechar);
        }
        return transfer;
    }

    /**
     * 查询余额
     * @return
     */
    @Override
    public String queryMoney() throws Exception {
        PlatformContract platFormContract = PlatformContractService.load(Constants.PLATFORM_CONTRACT_ADDRESS);
        BigInteger balance = PlatformContractService.getBalance(platFormContract);
        return String.valueOf(balance);
    }

    @Override
    public List<Recharge> queryRecord(Map map) {
        return rechargeService.queryRecharge(map);
    }

    /**
     * 统计
     * @return
     */
    @Override
    public PlatformInfo statistics() {
        PlatformInfo platformInfo = new PlatformInfo();
        //查询用户数量
        CommonResult userCommonResult = userService.queryTotal();
        int userNum = JSON.parseObject(JSON.toJSONString(userCommonResult.getData()), Integer.class).intValue();
        platformInfo.setUserNumber(userNum);

        CommonResult manufacturerResult = manufacturerService.queryTotal();
        int manufacturersNum = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Integer.class).intValue();
        platformInfo.setManuNumber(manufacturersNum);

        CommonResult typeCommonResult = commodityService.queryTotal();
        int typeNum  = JSON.parseObject(JSON.toJSONString(typeCommonResult.getData()),Integer.class).intValue();
        platformInfo.setTypeNumber(typeNum);
        //查订单
        CommonResult orderResult = orderService.queryOrder(new HashMap());
        List<Order> orders = JSON.parseArray(JSON.toJSONString(orderResult.getData()), Order.class);

        HashMap<Integer,Integer> orderMap = new HashMap();
        orderMap.put(0,0);
        orderMap.put(1,0);
        orderMap.put(2,0);
        //遍历订单
        for (Order order : orders) {
            orderMap.put(order.getOrderType(),orderMap.get(order.getOrderType())+1);
        }

        int[] orderType = new int[3];
        //共享租赁订单
        orderType[0] = orderMap.get(0);
        //以租代售
        orderType[1] = orderMap.get(1);
        //先租后买
        orderType[2] = orderMap.get(2);

        platformInfo.setOrderType(orderType);

        //查收益
        List<Profit> profits = profitService.queryProfit(new HashMap());

        int sumProfit = 0;
        for (Profit profit : profits) {
            sumProfit += profit.getAmount();
        }
        platformInfo.setProfitNumber(sumProfit);
        //查利润
        return platformInfo;
    }
}
