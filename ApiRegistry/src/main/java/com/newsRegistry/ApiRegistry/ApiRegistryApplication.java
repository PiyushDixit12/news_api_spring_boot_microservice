package com.newsRegistry.ApiRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApiRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRegistryApplication.class, args);
		System.out.println("Api Registry Application started !");
	}

}
