package com.sankalp.api_gateway.filters;

import com.sankalp.api_gateway.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest()
                                                 .getHeaders()
                                                 .getFirst("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse()
                               .setComplete();
            }
            String token = authorizationHeader.split("Bearer ")[1];

            String userId = jwtService.getUserIdFromToken(token);

            ServerWebExchange webExchange = exchange.mutate()
                                                    .request(r -> r.header("X-User-Id", userId))
                                                    .build();

            return chain.filter(webExchange);
        };
    }

    public static class Config {

    }
}
