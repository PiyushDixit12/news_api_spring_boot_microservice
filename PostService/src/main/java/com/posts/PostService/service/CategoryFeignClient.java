package com.posts.PostService.service;

import com.posts.PostService.payload.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CATEGORYSERVICE")
public interface CategoryFeignClient {
    @GetMapping(path = "/api/category/{categoryId}")
     ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId);
}
