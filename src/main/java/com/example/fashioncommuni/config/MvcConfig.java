package com.example.fashioncommuni.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:/2024-capstone-fashion/temp/profileImageUpload/");

        registry.addResourceHandler("/postImages/**")
                .addResourceLocations("file:/2024-capstone-fashion/temp/postImageUpload/");
    }
}