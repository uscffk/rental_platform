package com.ffk.contract.service;

import com.ffk.contract.AuctionContract;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/1/22
 */
public class AuctionContractService {

    /**
     * 部署拍卖合约  这里需要打钱部署  因为这里在一个合约中调用了另一个合约
     * @param weiInitValue
     * @return
     * @throws Exception
     */
    public static String deploy(int weiInitValue) throws Exception {
        AuctionContract auction = AuctionContract.deploy(
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider(),
                BigInteger.valueOf(weiInitValue)
                ).send();
        return auction.getContractAddress();
    }

    /**
     * 根据合约地址加载拍卖合约
     * @param contractAddress
     * @return
     */
    public static AuctionContract load(String contractAddress){
        return AuctionContract.load(
                contractAddress,
                Environment.getWeb3j(),
                Environment.getCredential(),
                new DefaultGasProvider());
    }


    /**
     * 获取拍卖信息
     * @param
     * @return
     */
    public static Map<String, Object> getAuctionInfo(AuctionContract auction) throws Exception {
        HashMap<String, Object> result = new HashMap<>(6);
        result.put("beneficiary",auction.getAuctionInfo().send().component1());
        result.put("auctionEnd",auction.getAuctionInfo().send().component2());
        result.put("token",auction.getAuctionInfo().send().component3());
        result.put("highestBidder",auction.getAuctionInfo().send().component4());
        result.put("highestBid",auction.getAuctionInfo().send().component5());
        result.put("ended",auction.getAuctionInfo().send().component6());
        return result;
    }

    /**
     * 获取拍卖纪录
     * @param auction
     * @return
     */
    public static Map<String,List> getAuctionRecord(AuctionContract auction) throws Exception {

         HashMap<String, List> result = new HashMap<>(2);
         //竞拍人
         List<String> bidder = auction.getAuctionRecord().send().component1();
         //出价
         List<BigInteger> price = auction.getAuctionRecord().send().component2();
         result.put("bidder",bidder);
         result.put("price",price);
         return result;

    }

    /**
     * 获取拍卖状态
     * @param auction
     * @return
     */
    public static boolean getAuctionState(AuctionContract auction) throws Exception {
        return auction.auctionState().send().booleanValue();
    }

    /**
     * 结束拍卖
     * @param auction
     * @return
     * @throws Exception
     */
    public static TransactionReceipt endAuction(AuctionContract auction) throws Exception {
        return auction.endAuction().send();
    }

    /**
     * 竞拍
     * @param auction
     * @param bidPrice 出价
     * @param manufacturerAddress 商家地址
     * @throws Exception
     */
    public static void bid(AuctionContract auction,BigInteger bidPrice,String manufacturerAddress) throws Exception {
        auction.bid(manufacturerAddress, bidPrice).send();
    }

    /**
     *
     * @param biddingTime 拍卖持续时间
     * @param beneficiary 受益人
     * @param tokenId 要拍卖的NFT ID
     * @param rentOwnerNFTAddress 租赁权合约地址
     */
    public static void setAuctionParameter(AuctionContract auctionContract, BigInteger biddingTime,String beneficiary,BigInteger tokenId,String  rentOwnerNFTAddress) throws Exception {
        auctionContract.setAuctionParameter(biddingTime,beneficiary,tokenId,rentOwnerNFTAddress).send();
    }

}