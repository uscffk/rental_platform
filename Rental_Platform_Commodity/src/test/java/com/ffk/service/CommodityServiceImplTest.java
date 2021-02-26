package com.ffk.service;

import com.ffk.pojo.Commodity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author 房发科
 * @date 2021/2/18 16:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CommodityServiceImplTest {
    @Autowired
    private ICommodityServiceImpl commodityService;

//    @Test
//    public void addCommodity(){
//        Commodity commodity = new Commodity();
//        commodity.setCategory(1);
//        commodity.setManufacturer(2);
//        commodity.setRentPrice(50);
//        commodity.setSellPrice(100);
//        commodity.setName("充电宝");
//        commodity.setPicture("/pictures");
//        commodity.setNumber(10);
//        commodityService.addCommodity(commodity);
//    }

    public void deleteCommodity(){

    }
}
