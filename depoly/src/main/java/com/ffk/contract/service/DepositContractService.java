package com.ffk.contract.service;

import com.ffk.contract.DepositContract;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class DepositContractService {

    /**
     * 不打钱部署合约
   * @return
     * @throws Exception
   */
  public static String deploy() throws Exception {
    DepositContract depositContract = DepositContract.deploy(
            Environment.getWeb3j(),
            Environment.getCredential(),
            new DefaultGasProvider()
    ).send();
    return depositContract.getContractAddress();
  }

    /**
     * 加载合约
   * @param address
     * @return
     */
  public static DepositContract load(String address) {
    return DepositContract.load(
            address,
            Environment.getWeb3j(),
            Environment.getCredential(),
            new DefaultGasProvider());
  }

  /**
   * 获取押金池总余额
   * @param contract
   * @return
   * @throws Exception
   */
  public static BigInteger getBalance(DepositContract contract) throws Exception {
    return contract.getBalance().send();
  }

  /**
   * 退押金
   * @param address
   * @param amount
   * @param contract
   * @return
   * @throws Exception
   */
  public static boolean backDeposit(String address, BigInteger amount, DepositContract contract)
      throws Exception {
    BigInteger before = getBalance(contract);
    if (before.compareTo(amount) >= 0) {
      if (contract.backDeposit(address, amount).send().isStatusOK()) {
        return getBalance(contract).add(amount).compareTo(before) == 0;
      }
    }
    return false;
  }

  /**
   * 铸造押金NFT
   * @param userAddress
   * @param amount
   * @return
   */
  public static int mintDepositNFT(String userAddress, BigInteger commodityNFTId,BigInteger amount, DepositContract depositContract) throws Exception {
       if(depositContract.mintNFT(userAddress,commodityNFTId,amount).send().isStatusOK()){
         return depositContract.tokenCounter().send().intValue()-1;
       }else {
         return -1;
       }
  }


  /**
   * 通过NFT的ID返回NFT持有者
   * @param tokenId
   * @param depositContract
   * @return
   * @throws Exception
   */
  public static String ownerOf(int tokenId, DepositContract depositContract) throws Exception {
      return depositContract.ownerOf(BigInteger.valueOf(tokenId)).send();
  }

  /**
   * 查询NFT的元数据
   * @param tokenId
   * @param depositContract
   * @return
   * @throws Exception
   */
  public static Map<String, Object> getNFTMetaData(int tokenId, DepositContract depositContract) throws Exception {
    HashMap<String, Object> result = new HashMap<>();
    result.put("amount",depositContract.getNFTMetaData(BigInteger.valueOf(tokenId)).send().component1());
    result.put("effective",depositContract.getNFTMetaData(BigInteger.valueOf(tokenId)).send().component2());
    result.put("commodityNFTTokenId",depositContract.getNFTMetaData(BigInteger.valueOf(tokenId)).send().component3());
    return result;
  }


  /**
   * 查询某个账户所有持有的NFT
   * @param userAddress
   * @param depositContract
   * @return
   * @throws Exception
   */
  public static List getAllNFTByAddress(String userAddress, DepositContract depositContract) throws Exception {
    return depositContract.getAllNFTByAddress(userAddress).send();
  }





}
