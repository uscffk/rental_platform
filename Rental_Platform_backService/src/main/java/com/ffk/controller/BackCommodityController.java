package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.service.IBackCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 退租服务  只有共享租赁和先租后买需要退租 以租代售直接就是相当于买了 只是分期付款
 * 扫码->展示订单信息->点击退租->根据位置判断能否退租成功->调用支付服务->扣库存->改变NFT的使用权归为商家
 */
@RestController
public class BackCommodityController {
    @Autowired
    private IBackCommodityService backCommodityService;

    @RequestMapping(value = "/backCommodity",method = RequestMethod.POST)
    public CommonResult backCommodity(@RequestParam("orderId") int orderId,
                                      @RequestParam("longitude") String longitude,
                                      @RequestParam("latitude") String latitude,
                                      @RequestParam("nftId") int nftId){
        //调用退租服务
        try {
            int rs = backCommodityService.backCommodity(orderId,longitude,latitude,nftId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

}
