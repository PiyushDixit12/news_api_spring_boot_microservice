package com.newsGateway.ApiGateway.filter;

import com.newsGateway.ApiGateway.util.JwtUtil;
import com.newsGateway.ApiGateway.util.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("======================== " + exchange.getRequest().getURI());
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                try {
                    // REST call to AUTH service
//System.out.println("CHECKING IS VALID TOKEN");
//                    userFeignClient.isValidToken(authHeader);
                    jwtUtil.validateToken(authHeader);

                    System.out.println("token valid");
                } catch (Exception e) {
                    System.out.println("ERROR VALIDATING TOKEN");
                    e.printStackTrace();
                    System.out.println("invalid access...! " + e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties here
    }
}