package com.ffk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 房发科
 * @date 2021/2/26 17:03
 */
@SpringBootApplication
@EnableEurekaClient
public class PayServiceAPPlication {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceAPPlication.class,args);
    }
}
