package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @date 2021/2/26 15:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 要买这个东西
 */
public class RentForSale extends Order{
    /**
     * 订单编号
     */
    int id;

    /**
     * 商品id
     */
    int commodityId;

    /**
     * 用户id
     */
    int userId;

    /**
     * 订单状态 0：进行中 1：已退租 2：已购买
     */
    int status;

    /**
     * 租借期数
     */
    int timeNumber;

    /**
     * 实际付款
     */
    float actualPay;

    /**
     * 订单总金额
     */
    float sumMoney;

    /**
     * 当前期数
     */
    int  currTime;

    /**
     * 订单完成时间
     */
    String finishTime;

    /**
     * 订单创建时间
     */
    String startTime;


}
