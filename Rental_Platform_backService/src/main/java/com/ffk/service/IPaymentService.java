package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 房发科
 * @create 2022/3/4
 */
@FeignClient(value = "PayService")
public interface IPaymentService {
    @RequestMapping(value = "/payment/pay",method = RequestMethod.POST)
    CommonResult payment(@RequestParam(value = "orderId") int orderId);
}
