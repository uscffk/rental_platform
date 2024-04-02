package com.ffk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author 房发科
 * @date 2021/3/11 22:17
 */
@Configuration
public class Web3jConfig {
    @Bean
    public Web3j getWeb3j(){
        return Web3j.build(new HttpService());
    }
}
