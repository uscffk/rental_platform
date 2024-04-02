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
 * @date 2021/3/10 21:23
 */
@FeignClient("CommodityService")
public interface ICommodityService {

    /**
     * 查询商品
     * @param conditionMap
     * @return
     */
    @RequestMapping(value = "/commodities/query",method = RequestMethod.POST)
    CommonResult queryCommodity(@RequestBody Map conditionMap);

    /**
     * 按id查询商品
     * @param id 商品id
     * @return
     */
    @RequestMapping(value = "/commodities/queryById",method = RequestMethod.GET)
    CommonResult queryCommodityById(@RequestParam("id") int id);
}
