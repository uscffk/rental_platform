package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Recharge;
import com.ffk.service.IPlatformService;
import com.ffk.service.IRechargeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/9 23:47
 */
@RestController
@RequestMapping("/platforms")
public class PlatformController {
    @Autowired
    private IPlatformService platformService;

    @Autowired
    private IRechargeService rechargeService;

    /**
     * 充值
     * @param userId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    @HystrixCommand
    public CommonResult recharge(@RequestParam("userId") int userId,
                                 @RequestParam("amount") int amount,
                                 @RequestParam("from") int from){
        try {
            platformService.recharge(userId,amount,from);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
    }

    /**
     * 平台外部账户给平台合约账户充值
     * @param amount
     * @return
     */
    @RequestMapping(value = "/rechargeToSelf",method = RequestMethod.POST)
    public CommonResult rechargeToSelf(@RequestParam("amount") int amount){
        try {
            boolean rs = platformService.rechargeToSelf(amount);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 把平台合约账户的钱转到平台的外部账户
     * @return
     */
    @RequestMapping(value = "/transferToExteneralAccount",method = RequestMethod.POST)
    public CommonResult transferToExteneralAccount(){
        try{
            boolean rs = platformService.transferToExteneralAccount();
            if(rs){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 查询记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryRecord",method = RequestMethod.POST)
    public CommonResult queryRecord(@RequestBody Map map){
        try {
            System.out.println(map);
            List<Recharge> recharges = platformService.queryRecord(map);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",recharges);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 删除充值提现记录
     * 草率了 鬼知道我当时为什么不写一个RechargeController 懒得改了
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteRecharge",method = RequestMethod.POST)
    public CommonResult deleteRecord(@RequestParam("id") int id){
        try {
            int rs = rechargeService.deleteRecharge(id);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 提现
     * @param id 用户id
     * @param amount 金额
     * @param from 来源（用户 商家）
     * @return
     */
    @RequestMapping(value = "/getRMB",method = RequestMethod.POST)
    public CommonResult getRMB(@RequestParam("id") int id,
                               @RequestParam("amount") int amount,
                               @RequestParam("from") int from){
        try {
            boolean rs = platformService.getRMB(id, amount, from);
            if(rs){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 查询余额
     * @param
     * @return
     */
    @RequestMapping(value = "/queryBalance",method = RequestMethod.POST)
    public CommonResult getBalance(){

        try {
            String balance = platformService.queryMoney();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",balance);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
