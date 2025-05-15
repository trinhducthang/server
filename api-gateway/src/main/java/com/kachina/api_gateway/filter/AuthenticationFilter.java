package com.kachina.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachina.api_gateway.dto.request.VerifyTokenRequest;
import com.kachina.api_gateway.dto.response.ApiResponse;
import com.kachina.api_gateway.dto.response.VerifyTokenResponse;
import com.kachina.api_gateway.service.IdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final String TOKEN_PREFIX = "Bearer ";
    private final IdentityService identityService;
    private final ObjectMapper objectMapper;
    private final PathMatcher pathMatcher;

    private String[] public_endpoints = {
            "/api/identity/auth/**",
            "/eureka/**",
            "/api/company/list",
            "/api/company/id/**",
            "/api/job/with-company",
            "/api/job/enable-jobs"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Check public endpoint
        if(isPublicEndPoint(exchange.getRequest())) return chain.filter(exchange);
        // Get token form authorization header
        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(CollectionUtils.isEmpty(authHeaders)) return unauthentication(exchange.getResponse());
        String token = authHeaders.getFirst().replace(TOKEN_PREFIX, "");

        return identityService.verifyToken(token).flatMap(res -> {
            // Verify token
            if(res.getResult().isValid()) return chain.filter(exchange);
            else return unauthentication(exchange.getResponse());
        }).onErrorResume(throwable -> unauthentication(exchange.getResponse()));
    }

    public Mono<Void> unauthentication(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Unauthenticated")
                .error("Unauthenticated")
                .timestamp(new Date())
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            log.error("Can not write value as string:", e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndPoint(ServerHttpRequest request) {
        String requestPath = request.getURI().getPath();
        return Arrays.stream(public_endpoints).anyMatch(s -> pathMatcher.match(s, requestPath));
    }
}
