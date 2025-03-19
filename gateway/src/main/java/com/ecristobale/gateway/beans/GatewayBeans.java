package com.ecristobale.gateway.beans;

import com.ecristobale.gateway.filters.AuthFilter;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.netty.http.client.HttpClient;

import java.util.Set;

@Configuration
@AllArgsConstructor
public class GatewayBeans { //GatewayConfig

    private final AuthFilter authFilter;

    @Bean
    @Profile(value = "eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                    .path("/companies-crud/company/**")
                    .uri("http://localhost:8081")
                )
                .route(route -> route
                    .path("/report-ms/report/**")
                    .uri("http://localhost:7070")
                )
                .build();
    }

    @Bean
    @Profile("eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        .uri("lb://companies-crud") // lb: load balancer + name registered in Eureka
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .build();
    }

    @Bean
    @Profile("eureka-on-cb")
    public RouteLocator routeLocatorEurekaOnCB(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        .filters(filter -> {
                            filter.circuitBreaker(config ->
                                    config.setName("gateway-cb")
                                            .setStatusCodes(Set.of("400", "500"))
                                            .setFallbackUri("forward:/companies-crud-fallback/company/**"));
                            return filter;
                        })
                        .uri("lb://companies-crud") // lb: load balancer + name registered in Eureka
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .route(route -> route
                        .path("/companies-crud-fallback/company/**")
                        .uri("lb://companies-crud-fallback") // lb: load balancer + name registered in Eureka
                )
                .build();
    }

    @Bean
    @Profile("oauth2")
    public RouteLocator routeLocatorOauth2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/companies-crud/company/**")
                        .filters(filter -> {
                            filter.circuitBreaker(config ->
                                    config.setName("gateway-cb")
                                            .setStatusCodes(Set.of("400", "500"))
                                            .setFallbackUri("forward:/companies-crud-fallback/company/**"));
                            filter.filter(this.authFilter);
                            return filter;
                        })
                        .uri("lb://companies-crud") // lb: load balancer + name registered in Eureka
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .filters(filter -> filter.filter(this.authFilter))
                        .uri("lb://report-ms")
                )
                .route(route -> route
                        .path("/companies-crud-fallback/company/**")
                        .filters(filter -> filter.filter(this.authFilter))
                        .uri("lb://companies-crud-fallback") // lb: load balancer + name registered in Eureka
                )
                .route(route -> route
                        .path("/auth-server/auth/**")
                        .uri("lb://auth-server") // lb: load balancer + name registered in Eureka
                )
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
