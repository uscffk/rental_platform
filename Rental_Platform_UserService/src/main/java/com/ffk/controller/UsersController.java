package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import com.ffk.pojo.Users;
import com.ffk.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping(value = "/queryById",method = RequestMethod.POST)
    public  CommonResult queryById(@RequestParam(value = "id") int id){
        try{
            Users users = usersService.queryById(id);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/queryByAddress",method = RequestMethod.POST)
    public CommonResult queryByAddress(@RequestParam(value = "address") String address){
        try {
            Users users = usersService.queryByAddress(address);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public CommonResult  addUser(@RequestBody Users user){
        try {
            logger.info("注册用户:{}",user);
            //设置合约地址
            String userContractAddress = UserContractService.deploy();
            user.setContractAddress(userContractAddress);
            int rs = usersService.addUser(user);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");

    }
    /**
     * 查询用户
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryUsers",method = RequestMethod.POST)
    public CommonResult queryUser(@RequestBody Map map){
        if (map.size()>=0){
            try {
                List<Users> users = usersService.queryUsers(map);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",users);
            }catch (Exception e){
                e.printStackTrace();
                return new CommonResult(Constants.FAIL_CODE,"error",null);
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 更新用户
     * @param users
     * @return
     */
    @RequestMapping(value = "/updateUsers",method = RequestMethod.POST)
    public CommonResult updateUser(@RequestBody Users users){
        try {
            int rs = usersService.updateUser(users);
            if (rs > 0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
       return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 查询订单
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryOrders",method = RequestMethod.POST)
    public CommonResult queryOrder(@RequestBody Map map) throws Exception {
        System.out.println(map);
        try {
            List<Order> orders = usersService.queryOrders(map);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",orders);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 查询余额
     * @param username
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBalance",method = RequestMethod.POST)
    public CommonResult queryBalance(@RequestParam("username") String username) throws Exception {
        try {
            BigInteger bigInteger = usersService.queryBalance(username);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",bigInteger);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 查用户数量
     * @return
     */
    @RequestMapping(value = "/queryTotal",method = RequestMethod.POST)
    public CommonResult queryTotal(){
        try{
            int rs = usersService.queryTotal();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
