package com.ffk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 房发科
 * @date 2021/2/10 23:43
 */

@SpringBootApplication
@MapperScan("com.ffk.dao")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ffk"})
public class CommodityService {
    public static void main(String[] args) {
        SpringApplication.run(CommodityService.class,args);
    }
}
