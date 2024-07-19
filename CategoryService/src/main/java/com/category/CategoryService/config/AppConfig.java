package com.category.CategoryService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

//    BEAN FOR MODEL MAPPER
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}