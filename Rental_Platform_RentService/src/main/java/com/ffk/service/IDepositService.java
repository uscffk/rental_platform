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

    /**
     * 交押金
     * @param userAddress
     * @param commodityNFTToken
     * @param amount
     * @return
     */
    @RequestMapping(value = "/deposit/addDeposit",method = RequestMethod.POST)
    CommonResult mintDepositNFT(@RequestParam(value = "userAddress") String userAddress,
                                              @RequestParam(value = "commodityNFTToken") int commodityNFTToken,
                                              @RequestParam(value = "amount") int amount);


}
