package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/23 8:25
 */
@FeignClient("OrderService")
public interface IOrderService {


    /**
     * 查询订单
     * @param map
     * @return
     */
    @RequestMapping(value = "/orders/queryOrder")
    CommonResult queryOrder(Map map);




}
