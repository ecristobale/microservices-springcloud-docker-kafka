package com.ecristobale.gateway.beans;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.netty.http.client.HttpClient;

@Configuration
public class GatewayBeans { //GatewayConfig

//    @Bean
//    @Profile(value = "eureka-off")
//    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(route -> route
//                    .path("/companies-crud/company/*")
//                    .uri("http://localhost:8081")
//                )
//                .route(route -> route
//                    .path("/report-ms/report/*")
//                    .uri("http://localhost:7070")
//                )
//                .build();
//    }

//    @Profile(value = "eureka-on")
    @Bean
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/companies-crud/company/*")
                        .uri("lb://companies-crud") // lb: load balancer + name registered in Eureka
                )
                .route(route -> route
                        .path("/report-ms/report/*")
                        .uri("lb://report-ms")
                )
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
