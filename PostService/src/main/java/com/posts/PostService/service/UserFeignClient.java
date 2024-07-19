package com.posts.PostService.service;

import com.posts.PostService.payload.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE")
public interface UserFeignClient {
    @GetMapping(path = "api/user/{userId}")
     ResponseEntity<UserDto> getUser(@PathVariable long userId);

}
