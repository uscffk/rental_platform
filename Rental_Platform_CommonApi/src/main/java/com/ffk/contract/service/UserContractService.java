package com.ffk.contract.service;

import com.ffk.contract.UserContract;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class UserContractService {
  // 智能合约有些方法需要需要通过transaction执行，有些不需要（读取值的那些，
  // 看一下Contract方法里面的 RemoteFunctionCall<> 括号里面是TransactionReceipt就需要）
  // credentials可以用来发起交易、签署信息、发布智能合约、调用智能合约
  // solidity转java合约类后，就可以调用deploy发布
  // 初始时打钱，前提是构造方法支持，智能合约可以存钱，就像钱包一样



  /**
   * 不打钱部署
   * @return
   * @throws Exception
   */
  public static String deploy() throws Exception {
    UserContract contract =
        UserContract.deploy(
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider())
            .send();
    return contract.getContractAddress();
  }


  /**
   * 加载合约
   * @param address
   * @return
   */
  public static UserContract load(String address) {
    return UserContract.load(address, Environment.getWeb3j(),
                             Environment.getCredential(),
                             new DefaultGasProvider());
  }

  /**
   * 加信用
   * @param contract
   * @param amount  本笔交易的金额
   * @throws Exception
   */
  public static void increaseCredit(UserContract contract,BigInteger amount) throws Exception {
    contract.addCredit(amount).send();
  }

  /**
   * 扣信用
   * @param contract 用户合约
   * @throws Exception
   */
  public static void decreaseCredit(UserContract contract) throws Exception {
    contract.decreaseCredit().send();
  }

  /**
   * 获取信用值
   * @param contract
   * @return
   * @throws Exception
   */
  public static int getCredit(UserContract contract) throws Exception {
    return contract.getCredit().send().intValue();
  }

  /**
   * 获取余额 以wei为单位
   * @param contract
   * @return
   * @throws Exception
   */
  public static BigInteger getBalance(UserContract contract) throws Exception {
    return contract.getBalance().send();
  }

  /**
   * 用户付款 合约contact给address转amount个 wei
   * @param address 接收地址
   * @param amount 额度
   * @param contract 用户合约
   * @return
   * @throws Exception
   */
  public static boolean transfer(String address, BigInteger amount, UserContract contract)
      throws Exception {
    BigInteger before = getBalance(contract);
    if (before.compareTo(amount) >= 0) {
      if (contract.transfer(address, amount).send().isStatusOK()) {
        return getBalance(contract).add(amount).compareTo(before) == 0;
      }
    }
    return false;
  }

  /**
   * 添加订单Id 商品Id
   * @param orderId
   * @param goodId
   * @param contract
   * @return
   * @throws Exception
   */
  public static boolean addOrder(BigInteger orderId, BigInteger goodId, UserContract contract)
      throws Exception {
    if (contract.addOrder(orderId, goodId).send().isStatusOK()) {
      return getGoodIdByOrderId(orderId, contract) != -1;
    }
    return false;
  }

  /**
   * 通过订单Id获取商品Id
   * @param orderId
   * @param contract
   * @return
   * @throws Exception
   */
  public static int getGoodIdByOrderId(BigInteger orderId, UserContract contract) throws Exception {
    return contract.getGoodIdByOrderId(orderId).send().intValue();
  }


  /**
   * 获取所有订单Id
   * @param contract
   * @return
   * @throws Exception
   */
  public static List getAllOrdersId(UserContract contract) throws Exception {
    return  contract.getOrderIds().send();
  }

  /**
   * 获取租赁商品列表
   * @param contract
   * @return
   * @throws Exception
   */
  public static List getAllGoodsId(UserContract contract) throws Exception {
    return contract.getGoodIds().send();
  }

}
