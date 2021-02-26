package com.ffk.pojo;

/**
 * @author 房发科
 * @date 2021/2/26 15:29
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 先租不买 如果想买的话付的租金可以抵押一部分钱
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentBeforeBuy extends Order{

    /**
     * 订单id
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
     * 订单完成时间
     */
    String finishTime;

    /**
     * 订单开始时间
     */
    String startTime;

    /**
     * 订单状态
     */
    int status;

    /**
     * 体验期数
     */
    int useTime;

    /**
     * 总金额
     */
    float sumMoney;

    /**
     * 实际支付金额
     */
    float actualPay;

    /**
     * 当前期数
     */
    int currTime;
}
