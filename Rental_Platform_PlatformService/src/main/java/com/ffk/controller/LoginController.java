package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.dao.IPlatform;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 房发科
 * @date 2021/3/9 23:58
 */
@RestController
/**
 * 登录控制器
 */
public class LoginController {
    @Autowired
    private IPlatform platform;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              HttpServletRequest request){
        if("".equals(username)||"".equals(password)){
            return new CommonResult(Constants.FAIL_CODE,"用户名或密码不能为空");
        }else {
            //做查询
            Platform platforms = platform.queryPlatform(username);
            if (platforms!=null){
                if (password.equals(platforms.getPassword())){
                    //存入session
                    HttpSession session = request.getSession();
                    session.setAttribute(username,platforms);
                    return new CommonResult(Constants.SUCCESS_CODE,"ok",platforms);
                }else {
                    return new CommonResult(Constants.FAIL_CODE,"密码错误");
                }
            } else {
                return new CommonResult(Constants.FAIL_CODE,"账户不存在");
            }
        }
    }
}
