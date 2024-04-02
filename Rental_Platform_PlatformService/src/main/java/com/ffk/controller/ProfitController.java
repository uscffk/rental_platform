package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Profit;
import com.ffk.service.IProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/4 0:18
 */
@RestController
@RequestMapping("/profit")
public class ProfitController {
    @Autowired
    private IProfitService profitService;

    /**
     * 查询收益
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryProfit",method = RequestMethod.POST)
    public CommonResult queryProfit(@RequestBody Map map){
        try {
            System.out.println(map);
            List<Profit> profits = profitService.queryProfit(map);
            return new CommonResult<>(Constants.SUCCESS_CODE,"ok",profits);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 添加收益
     * @param profit
     * @return
     */
    @RequestMapping(value = "/addProfit",method = RequestMethod.POST)
    public CommonResult addProfit(@RequestBody Profit profit){
        try {
            int rs = profitService.addProfit(profit);
            if (rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

}
