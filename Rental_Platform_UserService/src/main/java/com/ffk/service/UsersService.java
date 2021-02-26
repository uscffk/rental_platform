package com.ffk.service;


import com.ffk.pojo.Users;

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
     * 注册
     * @param users
     * @return
     */
    int register(Users users);

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
    List<Users> queryUsers(Map map);
}
