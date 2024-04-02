package com.ffk.service;

import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 房发科
 * @date 2021/3/11 22:47
 */
@FeignClient("PlatformService")
public interface IPlatformService {
    /**
     * 充值
     * @param username
     * @param amount
     * @return
     */
    @RequestMapping(value = "/platforms/recharge",method = RequestMethod.GET)
    CommonResult recharge(@RequestParam("username") String username, @RequestParam("amount)") float amount);


    /**
     * 提现
     * @param username
     * @param amount
     * @return
     */
    @RequestMapping(value = "/platforms/getRMB",method = RequestMethod.GET)
    CommonResult getRMB(@RequestParam("username") String username, @RequestParam("amount") float amount);

    /**
     * 根据合约地址去查询余额
     * @param contractAddress 合约地址
     * @return 余额
     */
    @RequestMapping(value = "/platforms/queryBalance",method = RequestMethod.POST)
    String queryMoney(String contractAddress);
}
