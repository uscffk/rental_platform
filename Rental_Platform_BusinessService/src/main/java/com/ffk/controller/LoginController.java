package com.ffk.controller;

/**
 * @author 房发科
 * @date 2021/3/6 16:49
 */

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Manufacturer;
import com.ffk.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录控制器
 */
@RestController
public class LoginController {

    @Autowired
    private IManufacturerService manufacturerService;

    /**
     * 验证登录
     * @param
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              HttpServletRequest request){

        if("".equals(username)||"".equals(password)){
            return new CommonResult(Constants.FAIL_CODE,"用户名或密码不能为空",null);
        }else {
            //做查询
            Manufacturer manufacturer = manufacturerService.queryLogin(username);
            if (password.equals(manufacturer.getPwd())){
                //存入session
                HttpSession session = request.getSession();
                session.setAttribute("manufacturer",manufacturer);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",manufacturer);
            }else {
                return new CommonResult(Constants.FAIL_CODE,"用户名或密码错误",null);
            }
        }
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
    public CommonResult loginout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("manufacturer");
        return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
    }
}
