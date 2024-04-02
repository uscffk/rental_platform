package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import com.ffk.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/20 19:46
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/queryById",method = RequestMethod.POST)
    public CommonResult queryById(@RequestParam("id") int id){
        try {
            Order order = orderService.queryById(id);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",order);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    @RequestMapping(value = "/queryOrder",method = RequestMethod.POST)
    public CommonResult queryOrder(@RequestBody Map map){
        try {
            logger.info("查询条件:{}",map);
            List<Order> list = orderService.queryOrder(map);
            logger.info("订单:{}",list);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)
    public CommonResult addOrder(@RequestBody Order order){
        try {
            int rs = orderService.addOrder(order);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/updateOrder",method = RequestMethod.POST)
    public CommonResult updateOrder(@RequestBody Order order){
        try {
            int rs = orderService.updateOrder(order);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/queryByNFTID",method = RequestMethod.POST)
    public CommonResult queryOrderByNFTID(@RequestParam("nftId") int nftId){
        try {
            Order order = orderService.queryOrderByNFTID(nftId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/queryNFTByOrderId",method = RequestMethod.POST)
    public CommonResult queryNFTIDByOrderId(@RequestParam("orderId") int orderId) throws Exception {
        try{
            int rs = orderService.queryNFTIDByOrderId(orderId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);

    }

}
