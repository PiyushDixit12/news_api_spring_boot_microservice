package com.newsGateway.ApiGateway.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "Blog API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = @Server(url = "http://localhost:8080")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SpringDocConfiguration {

//    @Bean
//    public GroupedOpenApi customOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("gateway")
//                .pathsToMatch("/**")
//                .build();
//    }
    @Bean
    public GroupedOpenApi postsGroup() {
        return GroupedOpenApi.builder()
                .group("posts")
                .pathsToMatch("/api/post/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersGroup() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentsGroup() {
        return GroupedOpenApi.builder()
                .group("comments")
                .pathsToMatch("/api/comment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryGroup() {
        return GroupedOpenApi.builder()
                .group("category")
                .pathsToMatch("/api/category/**")
                .build();
    }
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("Student Management System API")
//                        .description("Student Management System API")
//                        .version("v0.0.1")
//                        .license(new License()
//                                .name("Apache 2.0")
//                        )).externalDocs(new ExternalDocumentation()
//                        .description("Student Management System API"));
//
//    }
}
