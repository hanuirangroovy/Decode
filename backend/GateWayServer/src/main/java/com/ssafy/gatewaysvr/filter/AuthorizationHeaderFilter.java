package com.ssafy.gatewaysvr.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 인가용으로만 사용
@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter(){
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            // exchange 객체로 request, response를 받는다.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "Not Found authorization Header", HttpStatus.UNAUTHORIZED);
            }

            log.info("AuthorizationHeaderFilter filter: request id -> {}", request.getId());

            // chain에다가 postfilter: exchange 추가
            // 비동기방식으로 단일값 추가히기 위해서 Mono: 웹 플럭스 타입으로 추가
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("AuthorizationHeaderFilter filter: response code -> {}", response.getStatusCode());
                    }));
        };
    }
    private Mono<Void> onError(ServerWebExchange exchange, String e, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        return response.setComplete();
    }

    public static class Config {

    }
}

