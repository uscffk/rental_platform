package com.ffk.pojo;

/**
 * @author 房发科
 * @date 2021/1/31 16:35
 * 以太坊钱包类
 */
public class ETHAccount {

    Integer id ;

    //保存文件名
    String keyStoreKey;

    //12个单词的助记词
    String memorizingWords;

    //钱包公钥16进制字符串表示
    String ethPublicKey;

    //钱包私钥16进制字符串表示
    String ethPrivateKey;

    //钱包地址
    String walletAddress;

    //企业id
    Integer terraceUserId;

    //密码秘钥
    String rsaPublicKey;

    //密码公钥
    String rsaPrivateKey;

    //加密后密码
    String walletPwd;

    Integer status;

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyStoreKey() {
        return keyStoreKey;
    }

    public void setKeyStoreKey(String keyStoreKey) {
        this.keyStoreKey = keyStoreKey;
    }

    public String getMemorizingWords() {
        return memorizingWords;
    }

    public void setMemorizingWords(String memorizingWords) {
        this.memorizingWords = memorizingWords;
    }

    public String getEthPublicKey() {
        return ethPublicKey;
    }

    public void setEthPublicKey(String ethPublicKey) {
        this.ethPublicKey = ethPublicKey;
    }

    public String getEthPrivateKey() {
        return ethPrivateKey;
    }

    public void setEthPrivateKey(String ethPrivateKey) {
        this.ethPrivateKey = ethPrivateKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Integer getTerraceUserId() {
        return terraceUserId;
    }

    public void setTerraceUserId(Integer terraceUserId) {
        this.terraceUserId = terraceUserId;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ETHAccounts{" +
                "id=" + id +
                ", keyStoreKey='" + keyStoreKey + '\'' +
                ", memorizingWords='" + memorizingWords + '\'' +
                ", ethPublicKey='" + ethPublicKey + '\'' +
                ", ethPrivateKey='" + ethPrivateKey + '\'' +
                ", walletAddress='" + walletAddress + '\'' +
                ", terraceUserId=" + terraceUserId +
                ", rsaPublicKey='" + rsaPublicKey + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                ", walletPwd='" + walletPwd + '\'' +
                '}';
    }
}
