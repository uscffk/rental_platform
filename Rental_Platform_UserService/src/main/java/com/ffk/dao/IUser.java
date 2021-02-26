package com.ffk.dao;

import com.ffk.pojo.Users;
import org.apache.catalina.User;
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
     * @return
     */
    List<Users> queryUsers(Map map);

    /**
     * 根据合约地址查询信用积分
     * @param contractAddress
     * @return
     */
    int queryCreditscore(String contractAddress);
}
