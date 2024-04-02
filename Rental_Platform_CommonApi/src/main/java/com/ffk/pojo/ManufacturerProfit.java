package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @date 2021/4/4 8:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class ManufacturerProfit {
    /**
     * 收益id
     */
    private int id;

    /**
     * 商家id
     */
    private int manufacturerId;

    /**
     * 商品id
     */
    private int commodityId;

    /**
     * 收益到账时间
     */
    private String profitTime;

    /**
     * 金额
     */
    private int amount;

    /**
     * 收益来源 收益来源（0正常收益【用户支付的租金】 1用户违约收益)
     */
    private int from;
}
