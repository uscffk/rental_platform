package com.ffk.controller;

import com.ffk.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 房发科
 * @date 2021/2/26 16:06
 */
@RestController
public class SharingRentController {
    @Qualifier("ISharingRentServiceImpl")
    @Autowired
    private IOrderService orderService;
}
