package com.ffk.pojo;

/**
 * @author 房发科
 * @date 2021/3/22 19:31
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 保存用户订单的隐私
 * 1.商品id
 * 2.订单id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class UserPrivate {
    private int commodityId;
    private int userId;
}
