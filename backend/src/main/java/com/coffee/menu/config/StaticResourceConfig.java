package com.coffee.menu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagesPath = System.getProperty("user.dir") + "/frontend/uploads/images/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesPath);
    }
}