package com.ffk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 房发科
 * @date 2021/4/1 18:52
 */
@Configuration
public class FileupLoad  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler是指你想在url请求的路径

        //addResourceLocations是图片存放的真实路径

        registry.addResourceHandler("/upload/**").addResourceLocations("file:C:\\Users\\房发科\\IdeaProjects\\Rental_Platform\\Rental_Platform_UserService\\src\\main\\resources\\static\\upload\\");
    }
}



