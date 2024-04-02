package com.ffk.controller;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.contract.service.DepositContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import com.ffk.service.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;


/**
 * @author: 房发科
 * @create: 2022/2/22 09:54
 */

@RestController
@RequestMapping("/rent")
public class RentController {

  @Autowired
  private IRentService rentService;

  @Autowired
  private IUserService userService;

  @Autowired
  private ICommodityService commodityService;

  @Autowired
  private IStockService stockService;

  @Autowired
  private IDepositService depositService;

  Logger logger = LoggerFactory.getLogger(RentController.class);

  /**
   * 根据nftId和用户ID判断是跳租赁页面还是跳订单/退租页面
   * @param nftId
   * @param userId
   * @return
   */
  @RequestMapping(value = "/judge",method = RequestMethod.POST)
  public CommonResult judge(@RequestParam("nftId") int nftId,@RequestParam("userId") int userId){
    try{
      logger.info("nftID:{}--userID:{}",nftId,userId);
      int judge = rentService.judge(nftId, userId);
      return new CommonResult(Constants.SUCCESS_CODE,"ok",judge);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new CommonResult(Constants.FAIL_CODE,"error",null);

  }

  /**
   * 通过订单id查找订单
   * @param orderId
   * @return
   */
  @PostMapping("getOrder")
  public CommonResult queryOrderById(@RequestParam("orderId") int orderId) {
    Order order = rentService.queryById(orderId);
    if (order != null){
      return new CommonResult(Constants.SUCCESS_CODE, "成功", order);
    } else {
      return new CommonResult(Constants.FAIL_CODE, "未找到", null);
    }
  }

  /**勿学我在controller写业务 terrible coding style  我懒得去改了 好心人封装转移一下吧
   * @param goodId 商品id
   * @param userId 用户id
   * @param method 租赁方式
   * @param totalTime 分期数
   * @param nftId 租赁物品的NFT ID
   * @return: commonResult
   */
  @PostMapping("/addOrder")
  @GlobalTransactional
  public CommonResult addOrder(
      @RequestParam("goodId") int goodId,
      @RequestParam("userId") int userId,
      @RequestParam(value = "method", defaultValue = "0") int method,
      @RequestParam(value = "totalTime", defaultValue = "0") int totalTime,
      @RequestParam(value = "nftId") int nftId)
      throws Exception {

    logger.info("租赁商品的ID{}->用户ID{}-->租赁模式{}-->期数{}-->NFTID{}",goodId,userId,method,totalTime,nftId);
    //租赁模式
    logger.info("租赁模式:{}---期数：{}",method,totalTime);
    //调用用户服务
    CommonResult commonResult = userService.queryById(userId);
    Users users = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Users.class);
    logger.info("用户信息:{}",users);

    //判断有无租赁资格
    if (users.getRentQualification() != Constants.HAS_RENT_QULIFICATION){
      return new CommonResult(Constants.FAIL_CODE, "你没资格租赁，请向平台申请重新获得租赁资格", null);
    }

    //调用商品服务
    CommonResult commodityResult = commodityService.queryCommodityById(goodId);
    Commodity commodity = JSON.parseObject(JSON.toJSONString(commodityResult.getData()), Commodity.class);
    logger.info("商品信息:{}",commodity);

    //判断是否支持相应租赁模式
    boolean flag =
        (method == Constants.SHARING_RENT /*&& commodity.getSharingRent() == Constants.SUPPORT*/)
                || (method == Constants.RENT_FOR_SALE
                /*&& commodity.getRentForSale() /*== Constants.SUPPORT*/)
                    || (method == Constants.RENT_BEFORE_BUY
                    /*&& commodity.getRentBeforeBuy() == Constants.SUPPORT*/);

    // 不支持该方式
    if (!flag) {
      return new CommonResult(Constants.FAIL_CODE, "不支持该租赁方式", null);
    }

    //最大期数
    int timeNumber = commodity.getTimeNumber();
    logger.info("该商品的最大期数为:{}",timeNumber);
    // 是否超过最大期数
    flag = totalTime <= commodity.getTimeNumber();
    if (!flag) {
      return new CommonResult(Constants.FAIL_CODE, "期数超过上限", null);
    }

    //获取用户合约地址
    String userContractAddress = users.getContractAddress();
    // 获取智能合约
    UserContract contract = UserContractService.load(userContractAddress);

    // 需要押金
    boolean need_deposit = true;

    //获取用户信用积分
    int credit = UserContractService.getCredit(contract);

    //免押
    if (credit >= Constants.NOT_NEED_DEPOSIT){
      need_deposit = false;
    }
    // 押金单位 wei
    if (need_deposit
        && UserContractService.getBalance(contract).intValue() < commodity.getDeposit()) {
        return new CommonResult(Constants.FAIL_CODE, "抱歉，您账户的余额小于所需的押金，请先充值", null);
    }

    //不需要押金
    if (!need_deposit){
      // 订单对应的押金，数据库存0
      commodity.setDeposit(0);
    }

    //获取商品的每期租赁价格
    int rentPrice = commodity.getRentPrice();
    //获取计费方式
    int billMethod = commodity.getBillMethod();
    //计算订单价格sumMoney
    int sumMoney = rentPrice*totalTime;
    // 创建订单
    Order order = new Order(commodity, method, totalTime,billMethod ,sumMoney);
    logger.info("新建的订单:{}",order);
    // 插入数据库
    int orderId = rentService.insert(order);

    //扣库存
    stockService.decreaseStock(commodity.getId(),1);

    //创建订单成功
    if (orderId > 0) {

      //智能合约存订单id-nftId
      //添加订单ID和商品ID
      UserContractService.addOrder(BigInteger.valueOf(orderId), BigInteger.valueOf(nftId), contract);

      //如果需要缴纳押金
      if (need_deposit) {

        logger.info("缴纳押金之前用户和押金账户的余额:{}----{}", UserContractService.getBalance(contract),
                DepositContractService.getBalance(DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS)));
        //需要押金
        //先给平台转钱 而后平台铸造押金NFT
        UserContractService.transfer(Constants.PLATFORM_CONTRACT_ADDRESS,
                BigInteger.valueOf(commodity.getDeposit()),
                contract); // 转押金
        //平台铸造押金NFT 所有权在用户手上
        depositService.mintDepositNFT(userContractAddress,nftId,commodity.getDeposit());

        logger.info("缴纳押金之后用户和押金账户的余额:{}",UserContractService.getBalance(contract),
                DepositContractService.getBalance(DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS)));
      }
        //转移商品NFT的所有权Or使用权  一开始是租赁 所以只需要改变 使用权和所有权一开始是在商家手中
        //加载商品合约
        CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
        //查询原NFT的使用权主人
        String ownerofUserightAddress = CommodityContractService.ownerofUseright(nftId, commodityNFTContract);
        //转移使用权
        CommodityContractService.transferUseRight(ownerofUserightAddress,userContractAddress,nftId,commodityNFTContract);
        //改变NFT的状态为使用中
        //查询
        CommonResult nftByNftResult = commodityService.queryNFTByNftId(nftId);
        NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftByNftResult.getData()), NFTCommodity.class);
        logger.info("NFTCommodity:{}",nftCommodity);
        //设置状态为使用中
        nftCommodity.setStatus(Constants.NFT_USING);
        //更新
        commodityService.updateNFTCommodity(nftCommodity);

      return new CommonResult(Constants.SUCCESS_CODE, "成功", order);
    }
    return new CommonResult(Constants.FAIL_CODE, "创建订单失败", null);
  }

  /**
   * 购买 只针对先租后买订单
   * @param money
   * @param orderId
   * @return
   */
  @RequestMapping(value = "/buyCommodity",method = RequestMethod.POST)
  public CommonResult buyCommodity(@RequestParam("money") int money,
                                   @RequestParam("orderId") int orderId,
                                   @RequestParam("userId") int userId) {
    boolean rs=false;
    try {
      rs = rentService.buyCommodity(money, orderId,userId);
      return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
    }catch (Exception e){
      e.printStackTrace();
    }
    return new CommonResult(Constants.FAIL_CODE,"error",rs);
  }

  /**
   * 计算购买需要的钱
   * @param orderId
   * @param userId
   * @return
   */
  @RequestMapping(value = "/calculateBuyMoney",method = RequestMethod.POST)
  public CommonResult calcuclateMoney(@RequestParam("orderId") int orderId,
                                      @RequestParam("userId") int userId){
    try{
      int rs = rentService.calculateBuyMoney(orderId, userId);
      return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new CommonResult(Constants.FAIL_CODE,"error",null);
  }

  /**
   * 续租
   * @param orderId
   * @return
   */
  @RequestMapping(value = "/reRent",method = RequestMethod.POST)
  public CommonResult reRent(@RequestParam("orderId") int orderId,
                             @RequestParam("time") int time,
                             @RequestParam("userId") int userId){

    String s=null;
    try {
      s = rentService.reRent(orderId, time, userId);
      return new CommonResult(Constants.SUCCESS_CODE,s);

    }catch (Exception e){
      e.printStackTrace();
    }
    return new CommonResult(Constants.FAIL_CODE,"error",s);
  }
}
