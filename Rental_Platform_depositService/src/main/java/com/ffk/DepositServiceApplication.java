package com.ffk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 房发科
 * @date 2021/2/26 17:20
 */
@EnableHystrixDashboard
@EnableHystrix
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@MapperScan("com.ffk.dao")
@EnableFeignClients(basePackages = {"com.ffk"})
public class DepositServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepositServiceApplication.class,args);
    }
}
