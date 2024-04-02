package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
public class Users {

    /**
     * 城市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 性别
     */
    private int sex;

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
     * 登录密码
     */
    private String password;

    /**
     * 合约地址
     */
    private String contractAddress;

    /**
     * 是否有租赁资格
     */
    private int rentQualification;

    /**
     * 信用积分
     */
    private int credit;

    /**
     * 余额
     */
    private int balance;

}
