package com.ffk.contract.service;

import com.ffk.contract.CommodityNFTContract;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class CommodityContractService {

    /**
     * 部署合约
     * @return
     * @throws Exception
     */
    public static String deploy() throws Exception {
            CommodityNFTContract commodityNFTContract =
                    CommodityNFTContract.deploy(
                            Environment.getWeb3j(),
                            Environment.getCredential(),
                            new DefaultGasProvider()).send();
            return commodityNFTContract.getContractAddress();
    }

    /**
     * 加载合约
     * @param contractAddress
     * @return
     */
    public static CommodityNFTContract load(String contractAddress){
        return CommodityNFTContract.load(contractAddress,
                                 Environment.getWeb3j(),
                                 Environment.getCredential(),
                                 new DefaultGasProvider());
    }

    /**
     * 铸造商品NFT
     * @param manufacturer
     * @param commodityNFTContract
     * @return NFT的ID
     */
    public static int MintCommodityNFT(String manufacturer, CommodityNFTContract commodityNFTContract) throws Exception {
        //判断该笔交易是否成功
         if(commodityNFTContract.mintNFT(manufacturer).send().isStatusOK()){
             return commodityNFTContract.tokenCounter().send().intValue()-1;
         }else {
             return -1;
         }

    }

    /**
     * 根据NFT ID返回所有权者
     * @param tokenId
     * @param commodityNFTContract
     * @return
     * @throws Exception
     */
    public static String ownerofOwnership(int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.ownerofOwnership(BigInteger.valueOf(tokenId)).send();
    }

    /**
     * 根据NFT ID返回使用权者
     * @param tokenId
     * @param commodityNFTContract
     * @return
     * @throws Exception
     */
    public static String ownerofUseright(int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.ownerofUseright(BigInteger.valueOf(tokenId)).send();
    }

    /**
     * 返回一个地址的所有NFT所有权
     * @param address
     * @return
     */
    public static List getAllOwnershipNFTByAddress(String address, CommodityNFTContract commodityNFTContract) throws Exception {
       return commodityNFTContract.getAllOwnershipNFTByAddress(address).send();
    }


    /**
     * 返回一个地址的所有NFT使用权
     * @param userAddress
     * @return
     */
    public static List getAllUseRightNFTByAddress(String userAddress, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.getAllUseRightNFTByAddress(userAddress).send();
    }

    /**
     * 返回商品评论
     * @param tokenId
     * @param commodityNFTContract
     * @return
     * @throws Exception
     */
    public static String getCommodityComment(int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.getCommodityComment(BigInteger.valueOf(tokenId)).send();
    }

    /**
     * 设置评论
     * @param commentIpfs
     * @param tokenId
     * @return
     */
    public static boolean setCommodityComment(String commentIpfs, int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
         return commodityNFTContract.setCommodityComment(commentIpfs,BigInteger.valueOf(tokenId)).send().isStatusOK();
    }

    /**
     * 转移所有权
     * @param from
     * @param to
     * @param tokenId
     * @param commodityNFTContract
     * @return
     * @throws Exception
     */
    public static boolean transferOwnerShip(String from, String to, int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.transferOwnerShip(from,to,BigInteger.valueOf(tokenId)).send().isStatusOK();

    }

    /**
     * 转移使用权
     * @param from
     * @param to
     * @param tokenId
     * @return
     */
    public static boolean transferUseRight(String from, String to, int tokenId, CommodityNFTContract commodityNFTContract) throws Exception {
        return commodityNFTContract.transferUseRight(from,to,BigInteger.valueOf(tokenId)).send().isStatusOK();
    }



}
