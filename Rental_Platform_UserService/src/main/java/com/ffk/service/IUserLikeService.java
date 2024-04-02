package com.ffk.service;

import com.ffk.pojo.UserLike;

import java.util.List;

/**
 * @author 房发科
 * @date 2021/4/9 17:06
 */
public interface IUserLikeService {

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
