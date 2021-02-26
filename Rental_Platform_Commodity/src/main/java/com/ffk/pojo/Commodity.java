package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 房发科
 * @date 2021/2/25 22:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {
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
    float rentPrice;

    /**
     * 商品出售价格
     */
    float sellPrice;

    /**
     * 商品厂商id
     */
    int manufacturer;

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
     * 经度
     */
    float longitude;

    /**
     * 纬度
     */
    float latitude;

    /**
     * 详细位置描述
     */
    String detailLoc;

    /**
     * 计费方式 0：按小时 1：按天  2：按月
     */
    int billMethod;

    /**
     * 商品状态 0：正常供应 1：维护中
     */
    int status;

    /**
     * 最大期数 主要用于以租代售（最大分期数）和先租后买（允许最大的体验时间）
     */
    int timeNumber;

    Stock stock;
    CommodityCategory categories;
}
