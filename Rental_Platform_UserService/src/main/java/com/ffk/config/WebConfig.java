package com.ffk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 房发科
 * @date 2021/3/5 22:05
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean("multipartResolver")
    public MultipartResolver configMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(501024100);
        commonsMultipartResolver.setMaxInMemorySize(4096);

        return commonsMultipartResolver;
    }

}
