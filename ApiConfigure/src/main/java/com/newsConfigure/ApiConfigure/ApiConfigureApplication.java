package com.newsConfigure.ApiConfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ApiConfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConfigureApplication.class, args);
		System.out.println("Api Configure Application started !");
	}

}
