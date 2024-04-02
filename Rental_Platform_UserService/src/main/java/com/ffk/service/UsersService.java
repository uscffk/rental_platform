package com.ffk.service;

import com.ffk.pojo.Order;
import com.ffk.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author: 房发科
 * @since 2021-02-20
 */
public interface UsersService
{
    /**
     * 添加用户
     * @param users
     * @return
     */
    int addUser(Users users);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(int id);

    /**
     * 修改及完善个人信息
     * @param users
     * @return
     */
    int updateUser(Users users);

    /**
     * 查找个人信息
     * @return
     */
    List<Users> queryUsers(Map map) throws Exception;

    /**
     * 登录
     * @param username
     * @return
     */
    Users login(String username) throws Exception;


    /**
     * 用户查询订单 可以查到自己租了什么商品
     * @param map
     * @return
     */
    List<Order> queryOrders(Map map) throws Exception;


    /**
     * 根据用户名查询余额
     * @param username
     * @return
     */
    BigInteger queryBalance(String username) throws Exception;

    /**
     * 按Id查询
     * @param id
     * @return
     */
    Users queryById(int id) throws Exception;

    /**
     * 查询用户数量
     * @return
     */
    int queryTotal();

    Users queryByAddress(@Param(value = "address") String address);

}
