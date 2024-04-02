package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.ManufacturerProfit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/24 0:05
 * 通过feign远程调用厂商服务
 * */
@FeignClient(value = "BusinessService")
public interface IManufacturerService {

    @RequestMapping(value = "/manufacturer/query",method = RequestMethod.POST)
    CommonResult queryManufacturer(Map map);


    @RequestMapping(value = "/profit/addProfit",method = RequestMethod.POST)
    CommonResult addProfit(@RequestBody ManufacturerProfit profit);

    @RequestMapping(value = "/manufacturer/queryById",method = RequestMethod.POST)
    CommonResult queryManufacturerById(@RequestParam(value = "manufacturerId") int manufacturerId);
}
