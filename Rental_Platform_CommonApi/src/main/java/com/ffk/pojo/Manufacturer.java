package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 房发科
 * @date 2021/3/3 11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class Manufacturer implements Serializable {
    /**
     * 厂商id
     */
    private int id;
    /**
     * 厂商名称
     */
    private String name;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String pwd;

    //合约地址
    private String contractAddress;

    /**
     * 余额
     */
    private int balance;

}
