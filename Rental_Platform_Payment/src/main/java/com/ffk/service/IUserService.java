package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/11 22:51
 */
@FeignClient("UserService")
public interface IUserService {
    /**
     * 查询反馈
     * @return
     */
    @RequestMapping(value = "/feedback/queryFeedback",method = RequestMethod.POST)
    CommonResult queryFeedback();


    /**
     * 查询用户
     * @param map
     * @return
     */
    @RequestMapping(value = "/users/queryUsers",method = RequestMethod.POST)
    CommonResult queryUser(Map map);

    /**
     * 更新用户
     * @param users
     * @return
     */
    @RequestMapping(value = "users/updateUsers",method = RequestMethod.POST)
    CommonResult updateUser(Users users);
}
