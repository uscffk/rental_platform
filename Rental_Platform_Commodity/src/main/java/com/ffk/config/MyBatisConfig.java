package com.ffk.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan({"com.ffk.dao"})
public class MyBatisConfig {
}
