package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.ManufacturerProfit;
import com.ffk.service.IManufacturerProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/4 9:09
 */
@RestController
@RequestMapping("/profit")
public class IManufacturerProfitController {
    @Autowired
    private IManufacturerProfitService manufacturerProfitService;

    /**
     * 查询收益
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryProfit",method = RequestMethod.POST)
    public CommonResult queryProfit(@RequestBody Map map){
        try {
            System.out.println(map);
            List<ManufacturerProfit> manufacturerProfits = manufacturerProfitService.queryManufacturerProfit(map);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",manufacturerProfits);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 添加收益记录
     * @param manufacturerProfit
     * @return
     */
    @RequestMapping(value = "/addProfit",method = RequestMethod.POST)
    public CommonResult addProfit(@RequestBody ManufacturerProfit manufacturerProfit){
        try {
            int rs = manufacturerProfitService.addManufacturerProfit(manufacturerProfit);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

}
