package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.service.IPaymentService;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 房发科
 * @create 2022/3/4
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    IPaymentService paymentService;

    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public CommonResult payment(@RequestParam(value = "orderId") int orderId){
        try {
            String payment = paymentService.payment(orderId);
            return new CommonResult(Constants.SUCCESS_CODE,"",payment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

}
