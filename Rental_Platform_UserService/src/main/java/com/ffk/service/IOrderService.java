package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/10 21:44
 */
@FeignClient("OrderService")
public interface IOrderService {
    @RequestMapping(value = "/orders/queryOrder",method = RequestMethod.POST)
    CommonResult queryOrder(@RequestBody Map map);
}
