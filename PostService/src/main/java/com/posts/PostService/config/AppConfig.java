package com.posts.PostService.config;

import com.posts.PostService.utils.ImageUpload;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public  ImageUpload imageUpload() {
        return new ImageUpload();
    }
//    BEAN FOR MODEL MAPPER
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
