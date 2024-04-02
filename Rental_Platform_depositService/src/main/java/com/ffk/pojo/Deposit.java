package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @create 2022/3/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {
    //nft Id
    int nftId;
    //金额
    int amount;
    //是否有效
    boolean isEffective;
    //对应的NFT Commodity
    NFTCommodity nftCommodity;
    //商品NFT
    int commodityNFT;
}
