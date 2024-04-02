package com.ffk.service;

import com.ffk.pojo.CommonResult;
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
    @RequestMapping(value = "/feedback/queryFeedback")
    CommonResult queryFeedback();


    /**
     * 查询用户
     * @param map
     * @return
     */
    @RequestMapping(value = "/users/queryUser",method = RequestMethod.POST)
    CommonResult queryUsers(Map map);
}
