package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Manufacturer;
import com.ffk.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/3 15:37
 */
@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;

    /**
     * 多条件查询
     * @param parameterMap
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public CommonResult queryManufacturers(@RequestBody Map parameterMap)  {
        if(parameterMap.size()>=0){
            try {
                List<Manufacturer> manufacturers = manufacturerService.queryManufacturer(parameterMap);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",manufacturers);
            }catch (Exception e){
                e.printStackTrace();
                return new CommonResult(Constants.FAIL_CODE,"error",null);
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 按Id查询
     * @param manufacturerId
     * @return
     */
    @RequestMapping(value = "/queryById",method = RequestMethod.POST)
    public CommonResult queryManufacturerById(@RequestParam(value = "manufacturerId") int manufacturerId) throws Exception {
        try{
            Manufacturer manufacturer = manufacturerService.queryManufacturerById(manufacturerId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",manufacturer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 添加厂商
     * @param manufacturer
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult addManufacturers(@RequestBody Manufacturer manufacturer){
        try {
            int rs = manufacturerService.addManufacturer(manufacturer);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 更新厂商
     * @param manufacturer
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult updateManufacturers(@RequestBody Manufacturer manufacturer){
        try {
            int rs = manufacturerService.updateManufacturer(manufacturer);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }
    /**
     * 查询余额
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBalance",method = RequestMethod.POST)
    public CommonResult queryBalance(@RequestParam("id") int id) throws Exception {
        //传入要查询厂商的id
            try {
                int balance = manufacturerService.queryBalance(id);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",balance);
            }catch (Exception e) {
                e.printStackTrace();
            }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public CommonResult login(@RequestParam("username") String username,@RequestParam("password") String password){
        Manufacturer manufacturer = manufacturerService.queryLogin(username);
        if (manufacturer!=null){
            //获取密码
            String pwd = manufacturer.getPwd();
            if (password.equals(pwd)){
                //验证成功
                return new CommonResult(Constants.SUCCESS_CODE,"ok",manufacturer);
            }else {
                //密码错误
                return new CommonResult(Constants.FAIL_CODE,"密码错误");
            }
        }else {
            return new CommonResult(Constants.FAIL_CODE,"账户不存在");
        }
    }
    /**
     * 查询反馈
     * @return
     */
    @RequestMapping(value = "/queryFeedBack",method = RequestMethod.POST)
    public CommonResult queryFeedBack(@RequestParam("manufacturerId") int manufacturerId){
        try {
            CommonResult commonResult = manufacturerService.queryFeedBack();
            return commonResult;
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
    }

    /**
     * 统计收益
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryProfit",method = RequestMethod.POST)
    public CommonResult queryProfit(@RequestBody Map map){
        System.out.println(map);
        CommonResult commonResult = manufacturerService.queryProfit(map);
        return commonResult;
    }

    /**
     * 查商家数量
     * @return
     */
    @RequestMapping(value = "/queryTotal",method = RequestMethod.POST)
    public CommonResult queryTotal(){
        try {
            int total = manufacturerService.getTotal();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",total);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }


}
