package com.ffk.contract.service;

import com.ffk.contract.RentOwnershipNFTContract;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class RentOwnershipNFTContractService {

    /**
     * 不打钱部署合约
     * @return
     * @throws Exception
     */
    public static String deploy() throws Exception {

        RentOwnershipNFTContract rentOwnershipNFTContract =  RentOwnershipNFTContract.deploy(Environment.getWeb3j(),
                                Environment.getCredential(),
                                new DefaultGasProvider()).send();
        return rentOwnershipNFTContract.getContractAddress();
    }

    /**
     * 根据合约地址加载合约
     * @param address
     * @return
     */
    public static RentOwnershipNFTContract load(String address){
        return RentOwnershipNFTContract.load(address,
                              Environment.getWeb3j(),
                              Environment.getCredential(),
                              new DefaultGasProvider()
                              );
    }

    /**
     * 铸造租赁权NFT
     * @param commodityName
     * @param location
     * @param rentOwnershipNFTContract
     * @return
     * @throws Exception
     */
    public static int mintRentNFT(String commodityName, String location, RentOwnershipNFTContract rentOwnershipNFTContract) throws Exception {
        if(rentOwnershipNFTContract.mintNFT(commodityName,location).send().isStatusOK()){
            return rentOwnershipNFTContract.tokenCounter().send().intValue()-1;
        }else {
            return -1;
        }
    }

    /**
     * 获得NFT的所有者
     * @param tokenId
     * @param rentOwnershipNFTContract
     * @return
     * @throws Exception
     */
    public static String ownerOf(int tokenId, RentOwnershipNFTContract rentOwnershipNFTContract) throws Exception {
        return rentOwnershipNFTContract.ownerOf(BigInteger.valueOf(tokenId)).send();
    }

    /**
     * 转移NFT
     * @param from
     * @param to
     * @param tokenId
     * @param rentOwnershipNFTContract
     * @return
     * @throws Exception
     */
    public static boolean transferNFT(String from, String to, int tokenId, RentOwnershipNFTContract rentOwnershipNFTContract) throws Exception {
        return rentOwnershipNFTContract.transferNFT(from,to,BigInteger.valueOf(tokenId)).send().isStatusOK();
    }

    /**
     * 获取NFT元数据
     * @param tokenId
     * @param rentOwnershipNFTContract
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getNFTMetaData(int tokenId, RentOwnershipNFTContract rentOwnershipNFTContract) throws Exception {

        HashMap<String, Object> result = new HashMap<>(2);
        result.put("commodityName",rentOwnershipNFTContract.getNFTMetaData(BigInteger.valueOf(tokenId)).send().component1());
        result.put("location",rentOwnershipNFTContract.getNFTMetaData(BigInteger.valueOf(tokenId)).send().component2());
        return result;
    }

    /**
     * 获取一个地址的所有NFT
     * @param manufacturerAddress
     * @return
     */
    public static List getAllNFTByAddress(String manufacturerAddress, RentOwnershipNFTContract rentOwnershipNFTContract) throws Exception {
        return rentOwnershipNFTContract.getAllNFTByAddress(manufacturerAddress).send();
    }





}
