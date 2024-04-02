package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 房发科
 * @date 2021/2/25 22:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity implements Comparable<Commodity>{

    /**
     * 商品id
     */
    int id;

    /**
     * 商品名称
     */
    String name;

    /**
     * 每期租赁价格
     */
    int rentPrice;

    /**
     * 商品出售价格
     */
    int sellPrice;


    /**
     * 商品类别id
     */
    int category;

    /**
     * 商品图片
     */
    String picture;

    /**
     * 商品描述
     */
    String discription;

    /**
     * 是否支持共享租赁 0：不支持 1：支持
     */
    int sharingRent;

    /**
     * 是否支持先租后买 0：不支持 1：支持
     */
    int rentBeforeBuy;

    /**
     * 是否支持以租代售
     */
    int rentForSale;

    /**
     * 详细位置描述
     */
    String detailLoc;

    /**
     * 计费方式 0：按小时 1：按天  2：按月
     */
    int billMethod;

    /**
     * 最大期数 用于以租代售（最大分期数）
     */
    int timeNumber;

    /**
     * 厂商Id
     */
    int manufacturerId;

    /**
     * 押金
     */
    int deposit;

    /**
     * 该商品所属的平台发布的 NFT ID
     */
    int rentOwnershipnft;

    /**
     * 该NFT被拍卖的合约地址 一个租赁权NFT ID对应一个拍卖合约
     */
    String auctionAddress;

    /**
     * 商品
     */
    List<NFTCommodity> nftCommodityList;

    /**
     * 库存
     */
    Stock stock;

    /**
     * 商品种类
     */
    CommodityCategory categories;

    /**
     * 厂商
     */
    Manufacturer manufacturers;

    /**
     * 租赁站点
     */
    List<BackLocation> backLocations;

    @Override
    public int compareTo(Commodity o) {
        //按Id排序
        int i = this.getId() - o.getId();
        return i;
    }
}
