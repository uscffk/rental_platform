package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Users;
import com.ffk.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 房发科
 * @date 2021/2/26 21:13
 */
@RestController
public class LoginController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestParam("username") String username, @RequestParam("password") String password,HttpServletRequest request) throws Exception {
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");

        if("".equals(username)||"".equals(password)){
            return new CommonResult(Constants.FAIL_CODE,"用户名或密码不能为空",null);
        }else{
            Users user = usersService.login(username);
            if(password.equals(user.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("user",user);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",user);
            }
            return new CommonResult(Constants.FAIL_CODE,"用户名或密码错误",null);
        }
    }
}
