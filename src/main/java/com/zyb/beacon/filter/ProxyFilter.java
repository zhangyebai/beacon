package com.zyb.beacon.filter;

import com.zyb.beacon.domain.dto.TokenDto;
import com.zyb.beacon.feign.TokenClient;
import com.zyb.beacon.utils.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * javadoc ProxyFilter
 * <p>
 *     http proxy filter
 * <p>
 * @author zhang yebai
 * @date 2021/6/8 8:17 PM
 * @version 1.0.0
 **/
@Component
@Slf4j
public class ProxyFilter implements GlobalFilter, Ordered {

    private TokenClient tokenClient;
    @Autowired
    public ProxyFilter setTokenClient(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
        return this;
    }

    private static final byte[] ILLEGAL_TOKEN_RESPONSE_BODY =
            "{\"code\":401,\"message\":\"access denied for illegal user token\",\"data\":null}"
                    .getBytes(StandardCharsets.UTF_8);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // add filter here
        final var opt = ProxyUtil.findFromCookie(exchange, "mytoken");
        if(opt.isPresent()){
            final var payload = tokenClient.findTokenPayload(new TokenDto().setToken(opt.get()));
            if(Objects.nonNull(payload)){
                return chain.filter(exchange.mutate().request(ProxyUtil.rebuildRequest(exchange, payload)).build());
            }
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return exchange.getResponse()
                .writeWith(
                        Mono.just(
                                exchange.getResponse().bufferFactory().wrap(ILLEGAL_TOKEN_RESPONSE_BODY)
                        )
                );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
