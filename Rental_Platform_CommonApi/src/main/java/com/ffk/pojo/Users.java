package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author author: 房发科
 * @since 2021-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users{

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户id
     */
    private int id;

    /**
     * 用户头像
     */
    private String userhead;

    /**
     * 身份证
     */
    private String identity;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 脸部信息
     */
    private String face;

    /**
     * 余额
     */
    private float balance;

    /**
     * 钱包地址
     */
    private String walletAddress;


}
