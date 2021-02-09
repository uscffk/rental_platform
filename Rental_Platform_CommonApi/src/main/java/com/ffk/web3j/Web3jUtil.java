package com.ffk.web3j;
import com.ffk.pojo.ETHAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.filters.FilterException;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import rx.Subscription;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 房发科
 * @date 2021/2/2 10:09
 */
public class Web3jUtil {
    @Autowired
    Web3j web3j;

    private Logger logger = LoggerFactory.getLogger(Web3jUtil.class);

    /**
     * 创建一个账户
     * @param walletPwd
     * @return
     * @throws Exception
     */
    public static Map<String, Object> newAccounts(String walletPwd) throws Exception {
        Map<String, Object> maps = new HashMap<>();
        ETHAccount ethAccounts = new ETHAccount();
        Bip39Wallet wallet;
        try {
            //本地环境
            wallet = WalletUtils.generateBip39Wallet(walletPwd, new File("D:/Geth/data0/keystore/"));
        } catch (Exception e) {
            throw new Exception("创建以太坊钱包失败");
        }

        //通过钱包密码与助记词获得钱包地址、公钥及私钥信息
        Credentials credentials = WalletUtils.loadBip39Credentials(walletPwd,
                wallet.getMnemonic());
        //钱包地址
        ethAccounts.setWalletAddress(credentials.getAddress());
        //钱包私钥16进制字符串表示
        ethAccounts.setEthPrivateKey(credentials.getEcKeyPair().getPrivateKey().toString(16));
        //钱包公钥16进制字符串表示
        ethAccounts.setEthPublicKey(credentials.getEcKeyPair().getPublicKey().toString(16));
        //保存文件名
        ethAccounts.setKeyStoreKey(wallet.getFilename());
        //12个单词的助记词
        ethAccounts.setMemorizingWords(wallet.getMnemonic());
        maps.put("ethAccounts",ethAccounts);
        return maps;
    }

    /**
     * 查看账户余额
     * @param address
     * @return
     * @throws Exception
     */
    public static Map<String, Object> accountsBanlance(String address) throws Exception {

        Web3j web3j = Web3j.build(new HttpService());
        Map<String, Object> maps = new HashMap<>();
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

            if (ethGetBalance != null) {
                maps.put("address",address);
                maps.put("banlance", Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER));
                System.out.println("账号地址：" + address);
                // 打印账户余额
                System.out.println("账号余额：" + ethGetBalance.getBalance());
                // 将单位转为以太
                System.out.println("账号余额：" + Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER)+"ETH");
            }
        }
        catch (ConnectException e){
            throw new ConnectException("################连接失败，客户端挂了");
        } catch (SocketTimeoutException exception){
            throw new SocketTimeoutException("###############连接超时，钱包地址有问题");
        }
        return maps;
    }

    /** 创建交易
     *
     */
    public  Map<String, Object> newTransaction(Integer num, String from, BigInteger value, String passWord,
                               String to, String keyStoreKey, String input) throws Exception {

        //建立连接
        Web3j web3j = Web3j.build(new HttpService());
        Map<String, Object> maps = new HashMap<>();
        try {
            //获取账户余额
            EthGetBalance ethGetBalance = web3j.ethGetBalance(from, DefaultBlockParameterName.LATEST).send();
            if (ethGetBalance != null&&ethGetBalance.getBalance().compareTo(value) == 1) {
                // 将单位转为以太，方便查看
                System.out.println("账号余额：" + Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER));
                // 第一个变量填入账户的密码，第二个变量填入账户文件的 path,可以在私链数据文件夹中的 keystore 文件夹中找到，是一个UTC开头的文件
                Credentials credentials = WalletUtils.loadCredentials(passWord, keyStoreKey);
                /*也可以通过私钥的方式*/
                /*  Credentials credentials = Credentials.create("xxxxxxxxxxxxx");*/
                //创建交易
                RawTransaction rawTransaction = RawTransaction.createTransaction(BigInteger.valueOf(num), Contract.GAS_PRICE,Contract.GAS_LIMIT,
                        to,  new BigInteger("1"), input);
                //签名
                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                String hexValue = Numeric.toHexString(signedMessage);
                //发起交易
                EthSendTransaction ethSendTransaction =
                        web3j.ethSendRawTransaction(hexValue).send();
                String transactionHash = ethSendTransaction.getTransactionHash();
                maps.put("transactionHash",transactionHash);
                System.out.println("交易的hash值:"+transactionHash);
            }else {
                throw new Exception("钱包账户余额不足");
            }
        }catch (ConnectException e){
            throw new ConnectException("################连接失败，客户端挂了");
        }catch (SocketTimeoutException exception){
            throw new SocketTimeoutException("###############连接超时，钱包地址有问题");
        }
        return maps;
    }


    /**
     * 通过交易hash获取交易信息
     * @param transactionHash
     * @return
     * @throws IOException
     */
    public Map<String,Object> getTransactionByHash( String transactionHash) throws IOException {
        Map<String,Object> map = new HashMap<>();
        Web3j web3j = Web3j.build(new HttpService());
        Optional<Transaction> et = web3j.ethGetTransactionByHash(transactionHash).send().getTransaction();
        Transaction transaction =  et.get();
        map.put("transaction",transaction);
        System.out.println(transaction.getHash()+"--"+transaction.getTo()+"--"+transaction.getBlockHash()+"--"+transaction.getFrom()+"--"+transaction.getInput());
        return map;
    }


    /**
     * 通过交易hash获取区块信息
     * @param transactionHash
     * @return
     * @throws IOException
     */
    public EthBlock getBlockByHash(String transactionHash) throws IOException{
        Web3j web3j = Web3j.build(new HttpService());
        //为true返回完整区块信息，false只返回交易hash
        EthBlock ethBlock = web3j.ethGetBlockByHash(transactionHash,true).send();
        return ethBlock;
    }

    /**
     * 查询交易记录
     * @return
     * @throws Exception
     */
    public Map listen() throws Exception {
        Web3j web3j = Web3j.build(new HttpService());
        Map<String, Object> maps = new HashMap<>();
        Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
            try {
                logger.info("New tx: id=[{}],blockHash=[{}],fromAddress=[{}],toAddress=[{}], value=[{}],input=[{}],nonce=[{}]",
                        tx.getHash(), tx.getBlockHash(),
                        tx.getFrom(), tx.getTo(),
                        tx.getValue().intValue(),
                        tx.getInput(),tx.getNonce());
                //获取总条数
                EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(tx.getFrom(), DefaultBlockParameterName.LATEST).send();
                logger.info("Tx count: {}", transactionCount.getTransactionCount().intValue());
            } catch (FilterException ee) {
                logger.error("这里有个bug：", ee);
            } catch (IOException e) {
                logger.error("这里有个bug:", e);
            }
        });
        return maps;
    }
}
