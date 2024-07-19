package com.newsGateway.ApiGateway.util;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "USERSERVICE")
public interface UserFeignClient {

    @PostMapping("/valid-token/{token}")
     ResponseEntity<Boolean> isValidToken(@PathVariable("token") String token);
}
