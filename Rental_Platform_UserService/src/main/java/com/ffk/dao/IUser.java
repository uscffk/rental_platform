package com.ffk.dao;

import com.ffk.pojo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/20 12:01
 */
@Repository
public interface IUser {
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
     * 修改用户信息
     * @param users
     * @return
     */
    int updateUser(Users users);

    /**
     * 查找用户
     * @param  map
     * @return
     */
    List<Users> queryUsers(Map map);

    /**
     * 登录
     * @param username
     * @return
     */
    Users login(String username);

    /**
     * 按Id查询
     * @param id
     * @return
     */
    Users queryById(@Param(value = "id") int id);

    /**
     * 查询用户数量
     * @return
     */
    int queryTotal();

    /**
     * 按地址查询
     * @param address
     * @return
     */
    Users queryByAddress(@Param(value = "address") String address);
}
