package com.ffk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

/**
 * @author 房发科
 * @date 2021/2/22 22:24
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 配置了Swagger的Docket的Bean实例
     * @return
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.enable(false) 是否启用swagger
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ffk.controller"))
                //过滤
                //.paths(PathSelectors.ant())
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("ffk","ffk.com" ,"1253308618@qq.com" );
        return new ApiInfo(
                "ffk的SwaggerAPI文档",
                "订单服务",
                "v1.0",
                "http:",
                contact,
                "Apache 2.0",
                "ffk.com",
                new ArrayList()
        );
    }
}
