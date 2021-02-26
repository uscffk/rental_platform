package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @date 2021/2/23 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    /**
     * 库存 id
     */
    private int id;

    /**
     * 商品id
     */
    private int commodityId;

    /**
     * 库存数量
     */
    private int  number;
}
