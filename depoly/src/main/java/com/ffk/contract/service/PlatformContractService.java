package com.ffk.contract.service;

import com.ffk.contract.PlatformContract;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class PlatformContractService {

  /**
   * 打钱部署合约
   * @param weiValue
   * @return  合约地址
   * @throws Exception
   */
  public static String deploy(BigInteger weiValue) throws Exception {
    PlatformContract contract =
        PlatformContract.deploy(
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider(),
                weiValue)
            .send();
    return contract.getContractAddress();
  }

  /**
   * 不打钱部署
   * @return  合约地址
   * @throws Exception
   */
  public static String deploy() throws Exception {
    PlatformContract contract =
        PlatformContract.deploy(
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider(),
                BigInteger.ZERO)
            .send();
    return contract.getContractAddress();
  }


  /**
   * 加载合约
   * @param address
   * @return
   */
  public static PlatformContract load(String address) {
    return PlatformContract.load(
            address, Environment.getWeb3j(),
            Environment.getCredential(),
            new DefaultGasProvider());
  }

  /**
   * 获取平台合约余额
   * @param contract
   * @return balance
   * @throws Exception
   */
  public static BigInteger getBalance(PlatformContract contract) throws Exception {
    return contract.getBalance().send();
  }

  /**
   * 平台给用户/商家充钱
   * @param address
   * @param amount
   * @param contract
   * @return
   * @throws Exception
   */
  public static boolean recharge(String address, BigInteger amount, PlatformContract contract)
      throws Exception {
    BigInteger before = getBalance(contract);
    if (before.compareTo(amount) >= 0) {
      if (contract.recharge(address, amount).send().isStatusOK()) {
        return getBalance(contract).add(amount).compareTo(before) == 0;
      }
    }
    return false;
  }

  /**
  * 把平台合约的钱全部转到外部账户
  * @param platformAddress
  * @return
  */
  public static boolean transferToAccount(String platformAddress,PlatformContract platform) throws Exception {
    return platform.transferToAccount(platformAddress).send().isStatusOK();
  }

  /**
   *  合约调用者 credential 给该合约充钱
   * @param amount
   * @param contract
   * @return
   * @throws Exception
   */
  public static boolean addMoney(BigInteger amount, PlatformContract contract) throws Exception {
    BigInteger before = getBalance(contract);
    if (contract.addMoney(amount).send().isStatusOK()) {
      return getBalance(contract).subtract(amount).compareTo(before) == 0;
    }
    return false;
  }


}
