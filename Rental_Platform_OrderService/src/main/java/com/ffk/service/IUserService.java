package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @create 2022/3/2
 */
@FeignClient("UserService")
public interface IUserService {
    /**
     * 按ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/queryById",method = RequestMethod.POST)
    CommonResult queryById(@RequestParam(value = "id") int id);


    /**
     * 查询用户
     * @param map
     * @return
     */
    @RequestMapping(value = "/users/queryUsers",method = RequestMethod.POST)
    CommonResult queryUser(Map map);
}
