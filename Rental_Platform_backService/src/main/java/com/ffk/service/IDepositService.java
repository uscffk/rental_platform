package com.ffk.service;

import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author 房发科
 * @create 2022/4/1
 */
@FeignClient(value = "DepositService")
public interface IDepositService {

    /**
     * 退押金
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deposit/backDeposit",method = RequestMethod.POST)
    CommonResult backDeposit(@RequestBody Map map);

}
