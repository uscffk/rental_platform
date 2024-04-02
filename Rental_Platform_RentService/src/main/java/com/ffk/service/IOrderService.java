package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/20 14:55
 */
@FeignClient(value = "OrderService")
public interface IOrderService {
    /**
     * 更新订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/orders/updateOrder",method =  RequestMethod.POST)
    CommonResult updateOrder(Order order);

    /**
     * 查询订单
     * @param map
     * @return
     */
    @RequestMapping(value = "/orders/queryOrder",method =  RequestMethod.POST)
    CommonResult queryOrder(Map map);

    /**
     *  添加订单
     * @param order
     * @return
     */
    @RequestMapping(value = "orders/addOrder",method =  RequestMethod.POST)
    CommonResult addOrder(@RequestBody Order order);

    /**
     * 按id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "orders/queryById",method =  RequestMethod.POST)
    CommonResult queryById(@RequestParam("id") int id);

}
