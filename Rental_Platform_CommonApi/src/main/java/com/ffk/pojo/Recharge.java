package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @date 2021/4/4 23:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class Recharge {
    /**
     * 记录id
     */
    private int id;

    /**
     * 用户身份 0用户 1商家
     */
    private int identity;

    /**
     * 用户id
     */
    private int fromId;

    /**
     * 金额
     */
    private int amount;

    /**
     * 操作类型 0充值 1提现
     */
    private int type;

    /**
     * 操作时间
     */
    private String time;

}
