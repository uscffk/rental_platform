package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 房发科
 * @date 2021/2/25 22:02
 */
@FeignClient(value = "StockService")
public interface IStockService {
    @RequestMapping(value = "/stocks/{id}",method = RequestMethod.GET)
    CommonResult queryStock(@PathVariable("id") int id);
}
