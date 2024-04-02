package com.ffk.contract.service;

import com.ffk.contract.ManufacturerContract;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class ManufacturerContractService {


  /**
   * 部署合约 不打钱
   * @return
   * @throws Exception
   */
  public static String deploy() throws Exception {
    ManufacturerContract contract =
        ManufacturerContract.deploy(
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider())
            .send();
    return contract.getContractAddress();
  }


  /**
   * 加载合约  调用之前要加载
   * @param address
   * @return
   */
  public static ManufacturerContract load(String address) {
    return ManufacturerContract.load(
            address,
            Environment.getWeb3j(),
            Environment.getCredential(),
            new DefaultGasProvider());
  }

  /**
   * 获取余额
   * @param contract
   * @return
   * @throws Exception
   */
  public static BigInteger getBalance(ManufacturerContract contract) throws Exception {
    return contract.getBalance().send();
  }

  /**
   * 上架商品 合约contact给address转amount个 wei
   * @param address 平台账户的地址
   * @param amount  钱的数目
   * @param contract 商家合约地址
   * @return  true：上架成功   false 上架失败
   * @throws Exception
   */
  public static boolean addCommodity(String address, BigInteger amount, ManufacturerContract contract)
      throws Exception {
    //先获取自己的余额
    BigInteger before = getBalance(contract);
    if (before.compareTo(amount) >= 0) {
      if (contract.addCommodity(amount, address).send().isStatusOK()) {
        return getBalance(contract).add(amount).compareTo(before) == 0;
      }
    }
    return false;
  }

  /**
   * 提现
   * @param manufacturerContract
   * @param amount
   * @param platforms
   * @return
   */
  public static boolean getRMB(ManufacturerContract manufacturerContract, BigInteger amount, String platforms) throws Exception {
    return manufacturerContract.getRMB(amount,platforms).send().isStatusOK();
  }

}
