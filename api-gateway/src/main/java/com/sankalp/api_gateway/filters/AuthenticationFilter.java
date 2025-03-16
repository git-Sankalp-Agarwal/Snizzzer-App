package com.sankalp.api_gateway.filters;

import com.sankalp.api_gateway.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
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
            String token = authorizationHeader.split("Bearer ")[1]; // Remove 'Bearer ' prefix
            String userData;
            try {
                userData = jwtService.getUserIdFromToken(token);
            } catch (ExpiredJwtException ex) {
                throw new RuntimeException("Token has expired. Please log in again.");
            } catch (SignatureException | MalformedJwtException ex) {
                throw new RuntimeException("Invalid token signature.");
            }

            String userId = userData.split("!")[0];

            String userName = userData.split("!")[1];

            String userRoles = jwtService.getUserRolesFromToken(token);

            String roles = userRoles.substring(1, userRoles.length() - 1);

            ServerWebExchange webExchange = exchange.mutate()
                                                    .request(r -> r.header("X-User-Id", userId)
                                                                   .header("X-User-Name", userName)
                                                                   .header("X-User-Roles", roles))
                                                    .build();

            return chain.filter(webExchange);
        };
    }

    public static class Config {

    }
}
