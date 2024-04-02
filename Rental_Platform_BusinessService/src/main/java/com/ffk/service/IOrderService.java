package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/10 21:23
 */
@FeignClient("OrderService")
public interface IOrderService {
    /**
     * 更新订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/orders/updateOrder")
    CommonResult updateOrder(Order order);

    /**
     * 查询订单
     * @param map
     * @return
     */
    @RequestMapping(value = "/orders/queryOrder")
    CommonResult queryOrder(Map map);
}
