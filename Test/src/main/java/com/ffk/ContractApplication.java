package com.ffk;

import com.ffk.constract.HelloContract;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author 房发科
 * @date 2021/2/25 17:42
 */
public class ContractApplication {
    public static void main(String[] args) throws Exception {
        Web3j web3j = Web3j.build(new HttpService());
        Credentials credentials = WalletUtils.loadCredentials("123456", "D:\\Geth\\testchain\\keystore\\UTC--2021-01-31T13-51-56.569061000Z--9988d557c831409b308a19e8fbade4caee6b326f");
        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPrivateKey());
        System.out.println(credentials.getEcKeyPair().getPublicKey());
//        System.out.println("合约部署者:"+credentials.getAddress());
//      // HelloContract helloContract = HelloContract.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
        //System.out.println("合约地址:"+helloContract.getContractAddress());
        //调用合约
        String smartContractAdress = "0xd12d834c0994101421c0c44f92807493f3848e52";
        HelloContract helloContract = HelloContract.load(smartContractAdress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        helloContract.setInfo("ffk", BigInteger.valueOf(18)).send();
        TransactionReceipt send = helloContract.getInfo().send();
        System.out.println("合约地址"+helloContract.getContractAddress());

//        System.out.println(send.getTransactionHash());
//        System.out.println(send.getLogs());
//        System.out.println(send.getContractAddress());
//        System.out.println(send.getBlockHash());
//        System.out.println(send.getStatus());
    }
}
