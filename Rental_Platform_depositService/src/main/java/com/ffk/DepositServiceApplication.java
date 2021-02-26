package com.ffk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 房发科
 * @date 2021/2/26 17:20
 */
@SpringBootApplication
@EnableEurekaClient
public class DepositServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepositServiceApplication.class,args);
    }
}
