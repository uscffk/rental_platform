package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Profit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 房发科
 * @date 2021/4/4 10:38
 */
@FeignClient("PlatformService")
public interface IPlatformService {
    /**
     * 增加收益记录
     * @param profit
     * @return
     */
    @RequestMapping(value = "/profit/addProfit",method = RequestMethod.POST)
    CommonResult addProfit(@RequestBody Profit profit);
}
