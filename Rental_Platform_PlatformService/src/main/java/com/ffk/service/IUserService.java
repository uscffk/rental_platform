package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/9 23:41
 */
@FeignClient("UserService")
public interface IUserService {
    /**
     * 查询用户
     * @param map
     * @return
     */
    @RequestMapping(value = "/users/queryUsers",method = RequestMethod.POST)
    CommonResult queryUsers(@RequestBody Map map);

    @RequestMapping(value = "/users/queryTotal",method = RequestMethod.POST)
    CommonResult queryTotal();

    /**
     * 按ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/queryById",method = RequestMethod.POST)
    CommonResult queryById(@RequestParam(value = "id") int id);

}
