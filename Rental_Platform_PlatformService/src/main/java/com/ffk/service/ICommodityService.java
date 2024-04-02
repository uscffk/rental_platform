package com.ffk.service;


import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author 房发科
 * @date 2021/3/10 21:23
 */
@FeignClient("CommodityService")
public interface ICommodityService {

    /**
     * 查询所有分类
     * @return
     */
    @RequestMapping(value = "/categories/",method = RequestMethod.GET)
    CommonResult queryAllType();

    @RequestMapping(value = "/categories/queryTotal",method = RequestMethod.POST)
    CommonResult queryTotal();
}
