package web;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("management-service",
                        r -> r.path("/api/v1/user-management/**", "/api/v1/auth/**", "/api/v1/company/**")
                                .uri("lb://management-service"))
                .route("email-service",
                        r -> r.path("/api/v1/email/**")
                                .uri("lb://email-service"))
                .route("profile-service",
                        r -> r.path("/api/v1/profile/**")
                                .uri("lb://profile-service"))
                .build();

    }
}