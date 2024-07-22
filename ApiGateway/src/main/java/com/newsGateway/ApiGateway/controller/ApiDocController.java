package com.newsGateway.ApiGateway.controller;

import com.ctc.wstx.dtd.ModelNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api-docs")
public class ApiDocController {

    private final WebClient webClient = WebClient.create();

    @GetMapping
    public Mono<String> getDocs(@RequestParam String service) {
        return webClient.get()
                .uri("http://{service}/v3/api-docs", service)
                .retrieve()
                .bodyToMono(String.class);
    }
}