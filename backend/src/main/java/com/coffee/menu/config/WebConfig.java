package com.coffee.menu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешаем запросы с адреса, где работает Vite
        registry.addMapping("/**") // Применяем ко всем API-путям
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешаем нужные HTTP методы
                .allowedHeaders("*");
    }
}