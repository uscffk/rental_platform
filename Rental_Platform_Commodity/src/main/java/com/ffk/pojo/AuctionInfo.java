package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionInfo {


    //拍卖记录
    //result.put("bidder",bidder);
    //result.put("price",price);
    Map<String, List> record;
    //拍卖信息
    //result.put("beneficiary",auction.getAuctionInfo().send().component1());
    //result.put("auctionEnd",auction.getAuctionInfo().send().component2());
    //result.put("token",auction.getAuctionInfo().send().component3());
    //result.put("highestBidder",auction.getAuctionInfo().send().component4());
    //result.put("highestBid",auction.getAuctionInfo().send().component5());
    //result.put("ended",auction.getAuctionInfo().send().component6());
    Map<String, Object> auctionInfo;
    //商品名称
    String name;
    //类别
    String category;
    //区域
    String detailLoc;
    //租赁权NFT Id
    int rentNftToken;
    //拍卖状态
    boolean status;
    //合约地址
    String auctionAddress;
}
