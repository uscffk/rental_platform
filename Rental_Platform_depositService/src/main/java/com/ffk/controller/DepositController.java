package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.contract.DepositContract;
import com.ffk.contract.service.DepositContractService;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Deposit;
import com.ffk.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/17 21:47
 */
@RestController
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;

    /**
     * 退押金
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/backDeposit",method = RequestMethod.POST)
    public CommonResult backDeposit(@RequestBody Map map) throws Exception {
        int orderId = (int) map.get("orderId");
        //退押金
        try {
            boolean rs = depositService.backDeposit(orderId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 交押金
     * @param userAddress
     * @param commodityNFTToken
     * @param amount
     * @return
     */
    @RequestMapping(value = "/addDeposit",method = RequestMethod.POST)
    public CommonResult mintDepositNFT(@RequestParam(value = "userAddress") String userAddress,
                                       @RequestParam(value = "commodityNFTToken") int commodityNFTToken,
                                       @RequestParam(value = "amount") int amount){
        try{
            boolean rs = depositService.mintDepositNFT(userAddress, commodityNFTToken, amount);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);

    }

    /**
     * 查询所有押金
     * @return
     */
    @RequestMapping(value = "/queryAllDeposit",method = RequestMethod.POST)
    public CommonResult queryAllDeposit(){
        try{
            List<Deposit> deposits = depositService.queryAllDeposit();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",deposits);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 按用户Id查询押金
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryDepositByUserId",method = RequestMethod.POST)
    public CommonResult queryByUserId(@RequestParam(value = "userId") int userId){
        try {
            List<Deposit> deposit = depositService.queryByUserId(userId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",deposit);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }


    @RequestMapping(value = "/queryDepositPoolBalance",method = RequestMethod.POST)
    public CommonResult queryDepositPoolBalance(){
        try {
            DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
            int balance = DepositContractService.getBalance(depositContract).intValue();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error");
    }


}
