package com.ffk.service;

import com.ffk.dao.IUser;
import com.ffk.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author: 房发科
 * @since 2021-02-20
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private IUser user;
    @Override
    public int register(Users users) {
        return user.addUser(users);
    }

    @Override
    public int deleteUser(int id) {
        return user.deleteUser(id);
    }

    @Override
    public int updateUser(Users users) {
        return user.updateUser(users);
    }

    @Override
    public List<Users> queryUsers(Map map) {
        return user.queryUsers(map);
    }
}
