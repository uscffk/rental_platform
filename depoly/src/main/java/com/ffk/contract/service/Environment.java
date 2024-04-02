package com.ffk.contract.service;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

/**
 * @program: maven_java_hll
 * @description:
 * @author: Mr.Hu
 * @create: 2021-02-07 08:25
 */
public class Environment {
  private static final Credentials credentials =
      Credentials.create("2e1397f1b0129d9d40aad2fea2c2e9eee81cac5ffc91ce276c20278109449b07");
  private static final String clientUrl = "http:127.0.0.1:7545"; // 连接d1节点
  private static final Web3j Web3j = initWeb3j();
  private static final Admin ADMIN = initAdmin();

  public static Admin getADMIN() {
    return ADMIN;
  }

  public static Credentials getCredential() {
    return credentials;
  }

  public static org.web3j.protocol.Web3j getWeb3j() {
    return Web3j;
  }


  /** 初始化admin级别操作的对象 */
  private static Admin initAdmin() {
    return Admin.build(getService());
  }

  /** 初始化web3j普通api调⽤用 */
  private static Web3j initWeb3j() {
    return org.web3j.protocol.Web3j.build(getService());
  }
  /** 通过http连接到geth节点 */
  private static HttpService getService() {
    return new HttpService(clientUrl);
  }
}
