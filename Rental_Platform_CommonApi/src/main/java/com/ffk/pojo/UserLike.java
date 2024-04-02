package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @date 2021/4/9 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class UserLike {
    /**
     * 收藏id
     */
    private int id;

    /**
     * 商品id
     */
    private int comId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 商品
     */
    Commodity commodity;
}
