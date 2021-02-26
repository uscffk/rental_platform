package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @date 2021/2/26 14:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharingRent extends Order{
    /**
     * 订单id
     */
    int id;

    /**
     * 用户ID
     */
    int userId;

    /**
     * 商品ID
     */
    int commodityId;

    /**
     * 订单状态
     */
    int status;

    /**
     ** 订单完成时间
     */
    String finishTime;

    /**
     * 订单开始时间
     */
    String startTime;

    /**
     * 租借时长（以期为单位）
     */
    int rentTime;

    /**
     * 实际支付金额
     */
    float actualPay;

    /**
     * 总需付金额
     */
    float sumMoney;

    /**
     * 当前期数
     */
    int currTime;
}
