package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.DepositContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.contract.service.DepositContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class RentServiceImpl implements IRentService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private IDepositService depositService;

    Logger logger = LoggerFactory.getLogger(RentServiceImpl.class);

    /**
     * 续租  这个功能貌似没必要了 这个功能在服外第一个版本有 第二个版本我舍弃了
     * ps:共享租赁 先租后买支持续租
     * @param orderId 订单id
     * @param time  续租期数
     * @param userId 用户Id
     */
    @Override
    public String reRent(int orderId,int time,int userId) throws Exception {
        //根据id查询订单
        CommonResult commonResult = orderService.queryById(orderId);
        Order order = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Order.class);
        //得到用户合约地址
        //查用户
        CommonResult userResult = userService.queryById(userId);
        //转对象
        Users users = JSON.parseObject(JSON.toJSONString(userResult.getData()), Users.class);
        //用户合约地址
        String userContractAddress = users.getContractAddress();
        //加载用户合约
        UserContract userContract = UserContractService.load(userContractAddress);
        //得到nft Id
        int nftId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
        //调用商品服务
        CommonResult nftResult = commodityService.queryNFTByNftId(nftId);
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);

        Commodity commodity = nftCommodity.getCommodity();
        //拿到商品的最大期数
        int maxTime = commodity.getTimeNumber();
        //边界条件
        if (time>maxTime){
            return "续租时长不能超过商品最大的租赁周期";
        }else {
            //得到之前一共要付的钱
            int preSumMoney = order.getSumMoney();
            //得到商品的租赁单价
            int rentPrice = commodity.getRentPrice();
            //新的订单总价格
            order.setSumMoney(preSumMoney+rentPrice*time);
            //更新订单状态为进行中
            order.setStatus(Constants.ORDER_ONGOING);
            //更新下一期支付时间
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            //判断之前是否已经付清但是没有退租
            if(order.getCurrTime()==order.getTotalTime()){
                //更新下一次支付时间
                //获取订单类型
                int orderType = order.getOrderType();
                //获取计费方式
                int billMethod = order.getBillMethod();
                //判断订单类型
                if(orderType == Constants.SHARING_RENT){    //共享租赁
                    if (billMethod == Constants.HOUR_RENT){
                        //按小时计费
                        Date date = new Date(calendar.getTimeInMillis() + time*Constants.HOUR_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        System.out.println(time);
                        order.setNextPay(nextPay);
                    }else if(billMethod == Constants.DAY_TIME){
                        //按天计费
                        Date date = new Date(calendar.getTimeInMillis() + time*Constants.DAY_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        order.setNextPay(nextPay);
                    }else {
                        //按月计费
                        Date date = new Date(calendar.getTimeInMillis() + time*Constants.MONTH_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        order.setNextPay(nextPay);
                    }
                }else{  //是先租后买订单
                    if(billMethod == Constants.HOUR_RENT){
                        //按小时计费
                        Date date = new Date(calendar.getTimeInMillis() + Constants.HOUR_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        System.out.println(nextPay);
                        order.setNextPay(nextPay);
                    }else if(billMethod == Constants.DAY_RENT){
                        //按天计费
                        Date date = new Date(calendar.getTimeInMillis() + Constants.DAY_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        order.setNextPay(nextPay);
                    }else {
                        //按月计费
                        Date date = new Date(calendar.getTimeInMillis() + Constants.MONTH_TIME);
                        String nextPay = simpleDateFormat.format(date);
                        order.setNextPay(nextPay);
                    }
                }
            }
            int preTotalTime = order.getTotalTime();
            //更新期数
            order.setTotalTime(time+preTotalTime);
            //更新订单
            logger.info("续租要更新的订单",order.toString());
            //更新订单
            orderService.updateOrder(order);
            return "成功";
        }

    }

    /**
     * 扫码的时候判断跳哪个页面
     * @param nftId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public int judge(int nftId, int userId) throws Exception {
        logger.info("NFT ID={} && UserId={}",nftId,userId);
        //先判断NFT的状态
        CommonResult commonResult = commodityService.queryNFTByNftId(nftId);

        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(commonResult.getData()), NFTCommodity.class);

        logger.info("NFT:{}",nftCommodity.toString());
        int status = nftCommodity.getStatus();
        logger.info("状态:{}",status);
        //查询这个 nftId是谁在租赁
        CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
        String ownerofUseright = CommodityContractService.ownerofUseright(nftId, commodityNFTContract);
        logger.info("租用者地址:{}",ownerofUseright);
        //根据用户 ID 查询
        CommonResult userResult = userService.queryById(userId);
        Users users = JSON.parseObject(JSON.toJSONString(userResult.getData()), Users.class);
        //得到用户地址
        String contractAddress = users.getContractAddress();
        logger.info("用户地址:{}",contractAddress);
        //判断
        if(status==Constants.NFT_FREE){
            //空闲跳转到租赁页面
            return 0;
        }else {
            //非空闲状态
            if(status==Constants.NFT_USING&&contractAddress.equals(ownerofUseright)){
                //跳订单/退租页面
                return 1;
            }
        }
        return 0;

    }

    /**
     * 计算先租后买需要支付的钱
     * @param orderId
     * @param userId
     * @return
     */
    @Override
    public int calculateBuyMoney(int orderId, int userId) throws Exception {
        //查扣谁的钱
        CommonResult userResult = userService.queryById(userId);
        //转用户对象
        Users user = JSON.parseObject(JSON.toJSONString(userResult.getData()), Users.class);
        //查地址
        String contractAddress = user.getContractAddress();
        //加载用户合约
        UserContract userContract = UserContractService.load(contractAddress);
        //调用用户合约 拿到 NFT ID  通过订单id查询 NFT ID
        int NFTIDByOrderId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
        CommonResult nftResult = commodityService.queryNFTByNftId(NFTIDByOrderId);
        //转成NFTCommodity对象
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);
        //商品对象
        Commodity commodity = nftCommodity.getCommodity();
        //获得商品出售的价格
        int sellPrice = commodity.getSellPrice();
        //获得商品每期的价格
        int rentPrice = commodity.getRentPrice();
        logger.info("商品出售价格为:{}",sellPrice);
        //查订单
        CommonResult orderResult = orderService.queryById(orderId);
        Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
        logger.info("要购买的订单:{}",order);
        //获取商家ID
        int manufacturerId = order.getManufacturerId();
        CommonResult manufacturerResult = manufacturerService.queryManufacturerById(manufacturerId);
        //转商家对象
        Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Manufacturer.class);
        //获取商家收益地址
        String manufacturerContractAddress = manufacturer.getContractAddress();

        //先租后买应支付的总金额 = 之前租赁的费用*0.6+售出价格 总不能之前租赁的钱白嫖吧 打个折就够了

        //------先计算之前租赁花了多少钱
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
        //计算购买之前租赁了多少期数
        int times = 0;
        if (billMethod == Constants.HOUR_RENT){ //按小时算钱
            times = (int) Math.ceil((now - time)/1000 / (60 * 60));

        }else if(billMethod == Constants.DAY_RENT){ //按天算钱
            times = (int) Math.ceil((now - time)/1000 / (60 * 60 * 24));

        }else {  //按月
            times = (int) Math.ceil((now - time)/1000 / ((60 * 60 * 24)*30));
        }
        //之前租赁的总价格
        int rentTotalPrice = times * rentPrice;
        //最终需要支付的金额
        double endPay = sellPrice + rentTotalPrice* Constants.RENT_BEFORE_BUY_RATE;

        // 转int
        // 解释一下为什么我所有钱相关的都用Int型表示，真正上线项目或者是公司的项目是不会这么去做的，
        // 这么做的原因是平台基于以太坊去交易 用wei做单位而不用以太（这个单位太大了）
        // 此外，做出效果就好了，用int能省不少事

        int endPays = (new Double(endPay)).intValue();
        logger.info("最终需要支付:{}",endPays);
        return endPays;
    }

    /**
     * 购买商品
     * ps:只有先租后买订单支持购买 如何计算购买差价问题
     * @param orderId
     */
    @Override
    @GlobalTransactional
    public boolean buyCommodity(int money,int orderId,int userId) throws Exception {

        //查订单
        CommonResult orderResult = orderService.queryById(orderId);
        Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
        logger.info("要购买的订单:{}",order);
        //获取商家ID
        int manufacturerId = order.getManufacturerId();
        CommonResult manufacturerResult = manufacturerService.queryManufacturerById(manufacturerId);
        //转商家对象
        Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(manufacturerResult.getData()), Manufacturer.class);
        //获取商家收益地址
        String manufacturerContractAddress = manufacturer.getContractAddress();

        //查用户
        //查扣谁的钱
        CommonResult userResult = userService.queryById(userId);
        //转用户对象
        Users user = JSON.parseObject(JSON.toJSONString(userResult.getData()), Users.class);
        //查地址
        String contractAddress = user.getContractAddress();
        //加载用户合约
        UserContract userContract = UserContractService.load(contractAddress);
        //调用用户合约 拿到 NFT ID  通过订单id查询 NFT ID
        int NFTIDByOrderId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
        CommonResult nftResult = commodityService.queryNFTByNftId(NFTIDByOrderId);
        //转成NFTCommodity对象
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);
        //商品对象
        Commodity commodity = nftCommodity.getCommodity();
        //支付 支付成功后修改订单状态
        //这里走支付服务
        boolean transfer = UserContractService.transfer(manufacturerContractAddress, BigInteger.valueOf(money), userContract);
        if (transfer){
            /*
            支付成功  即购买成功
            1.修改订单状态为已购买
            2.添加到商家收益
            库存不用加回去了
            加信用
            */
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            //设置订单完成时间
            String finishTime = simpleDateFormat.format(System.currentTimeMillis());
            order.setFinishTime(finishTime);
            //添加商家收益
            ManufacturerProfit manufacturerProfit = new ManufacturerProfit();
            manufacturerProfit.setAmount(money);
            manufacturerProfit.setFrom(0);
            manufacturerProfit.setCommodityId(commodity.getId());
            manufacturerProfit.setProfitTime(finishTime);
            //调用商家服务
            manufacturerService.addProfit(manufacturerProfit);
            //设置下一次还款时间    这个常量标志不用还了
            order.setNextPay(Constants.ORDER_END_NEXTPAY);
            //设置总金额 补交的加上之前交的
            order.setSumMoney(money);
            //设置实际支付的
            order.setActualPay(money);
            //退押金
            //加载押金合约
            DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
            //退押金
            //得到押金值
            int orderDeposit = order.getDeposit();
            HashMap<Object, Object> conditionHashMap = new HashMap<>();
            conditionHashMap.put("orderId",orderId);
            //退押金
            CommonResult commonResult = depositService.backDeposit(conditionHashMap);
            //退押金成功
            CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
            //改变NFT的所有权  由商家转给用户
            CommodityContractService.transferOwnerShip(manufacturerContractAddress,contractAddress,NFTIDByOrderId,commodityNFTContract);
            //更新订单
            //order.setDeposit(0);
            //订单类型更新为先租后买
            order.setOrderType(Constants.RENT_BEFORE_BUY);
            //订单状态为已购买
            order.setStatus(Constants.HAVE_BUY);
            //调用订单服务
            orderService.updateOrder(order);
            //调用商品服务 改变商品状态
            CommonResult nftByNftId = commodityService.queryNFTByNftId(NFTIDByOrderId);
            //改变状态
            nftCommodity.setStatus(Constants.NFT_SELLED);
            //更新NFT
            commodityService.updateNFTCommodity(nftCommodity);
            //加信用
            boolean statusOK = userContract.addCredit(BigInteger.valueOf(money)).send().isStatusOK();
            logger.info("加信用:{}",statusOK);
            return true;
        }
        return false;
    }

    @Override
    public boolean decreaseTime(Integer orderId) {
        Order order = queryById(orderId);
        if (order.getCurrTime() > 0) {
            order.setCurrTime(order.getCurrTime() - 1);
            return update(order) != null;
        }
        return false;
    }

    @Override
    public Integer getTotalTime(Integer orderId) {
        return queryById(orderId).getTotalTime();
    }

    @Override
    public Integer getCurrTime(Integer orderId) {
        return queryById(orderId).getCurrTime();
    }

    @Override
    public boolean addTotalPay(Integer orderId, Integer payment) {
        Order order = queryById(orderId);
        if (payment > 0) {
            order.setActualPay(order.getActualPay() + payment);
            return update(order) != null;
        }
        return false;
    }

    @Override
    public boolean finishOrder(Integer orderId) {
        Order order = queryById(orderId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setFinishTime(df.format(System.currentTimeMillis()));
        return update(order) != null;
    }

    @Override
    public boolean changeStatus(Integer orderId, Integer status) {
        Order order = queryById(orderId);
        order.setStatus(status);
        return update(order) != null;
    }

    @Override
    public int queryUserIdById(Integer orderId) {
//        return queryById(orderId).getUserId();
        return -1;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public Order queryById(Integer orderId) {
        //调用订单服务
        HashMap<Object, Object> orderHashMap = new HashMap<>(1);
        CommonResult commonResult = orderService.queryOrder(orderHashMap);
        return JSON.parseObject(JSON.toJSONString(commonResult.getData()),Order.class);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Order> queryAllByLimit(int offset, int limit) {
        HashMap<Object, Object> orderHashMap = new HashMap<>(2);
        orderHashMap.put("limit",limit);
        orderHashMap.put("offset", offset);
        CommonResult commonResult = orderService.queryOrder(orderHashMap);
        return JSON.parseArray(JSON.toJSONString(commonResult.getData()), Order.class);
    }

    /**
     * 新增数据
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(Order order) {
        //调用订单服务
        CommonResult commonResult = orderService.addOrder(order);
        //插入结果
        int orderId = (int) commonResult.getData();
        logger.info("新增加的订单Id:"+orderId);
        return orderId;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order update(Order order) {

        CommonResult commonResult = orderService.updateOrder(order);
        Order orders = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Order.class);
        return this.queryById(orders.getOrderId());
    }

    /**
     * 通过主键删除数据
     * 暂不用删除功能
     * @param orderId 主键
     * @return 是否成功
     */
}

