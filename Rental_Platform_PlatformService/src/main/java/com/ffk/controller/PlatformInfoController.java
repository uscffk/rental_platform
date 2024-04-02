package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.service.IPlatformInfo;
import com.ffk.vo.PlatformInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 房发科
 * @create 2022/3/12
 */
@RestController
@RequestMapping("/platInfo")
public class PlatformInfoController {

    @Autowired
    private IPlatformInfo platformInfo;

    @RequestMapping(value = "/queryPlatInfo",method = RequestMethod.POST)
    @HystrixCommand
    public CommonResult queryPlatformInfo(){
        try {
            PlatformInfo statistics = platformInfo.statistics();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",statistics);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
