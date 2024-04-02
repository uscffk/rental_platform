package com.ffk.dao;

import com.ffk.pojo.UserLike;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/4/9 17:03
 */
@Repository
public interface IUserLike {

    /**
     * 添加
     * @param userLike
     * @return
     */
    int addUserLike(UserLike userLike);

    /**
     * 按id删除
     * @param id
     * @return
     */
    int deleteUserLike(int id);

    /**
     * 按用户id查询
     * @return
     */
    List<UserLike> queryUserLike(int userId);

}
