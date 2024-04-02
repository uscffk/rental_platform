package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @date 2021/4/3 23:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class Profit {
    /**
     * 收益id
     */
    private int id;

    /**
     * 收益来源 收益来源（0商家上架商品，1用户违约）
     */
    private int from;

    /**
     * 收益金额
     */
    private int amount;

    /**
     * 收益到账时间
     */
    private String profitTime;

}
