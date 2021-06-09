package com.zyb.beacon.utils;

import com.google.common.collect.Lists;
import com.zyb.beacon.domain.CustomizedServerHttpRequestDecorator;
import com.zyb.beacon.domain.po.TokenPo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * javadoc ProxyUtil
 * <p>
 *     代理转发util
 * <p>
 * @author zhang yebai
 * @date 2021/6/9 4:17 PM
 * @version 1.0.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyUtil {

    /**
     * javadoc findFromHeader
     * @apiNote 从header中取指定key对应的第一个value
     *
     * @param exchange 请求
     * @param key header key
     * @return java.util.Optional<java.lang.String>
     * @author zhang yebai
     * @date 2021/6/9 4:31 PM
     **/
    public static Optional<String> findFromHeader(ServerWebExchange exchange, String key){
        final var values = listFromHeader(exchange, key);
        if(CollectionUtils.isNotEmpty(values)){
            return Optional.of(values.get(0));
        }
        return Optional.empty();
    }

    /**
     * javadoc listFromHeader
     * @apiNote 从header中列举指定key对应的value列表
     *
     * @param exchange 请求
     * @param key header key
     * @return java.util.List<java.lang.String>
     * @author zhang yebai
     * @date 2021/6/9 4:31 PM
     **/
    public static @NotNull List<String> listFromHeader(ServerWebExchange exchange, String key){
        final var headers = exchange.getRequest().getHeaders();
        final var values = headers.get(key);
        if(CollectionUtils.isNotEmpty(values)){
            return values;
        }
        return Lists.newArrayList();
    }

    /**
     * javadoc findFromCookie
     * @apiNote 从cookie中查询某键对应的第一个值
     *
     * @param exchange 请求
     * @param key cookie 键
     * @return java.util.Optional<java.lang.String>
     * @author zhang yebai
     * @date 2021/6/9 4:30 PM
     **/
    public static Optional<String> findFromCookie(ServerWebExchange exchange, String key){
        final var values = listFromCookie(exchange, key);
        if(CollectionUtils.isNotEmpty(values)){
            return Optional.of(values.get(0));
        }
        return Optional.empty();
    }

    /**
     * javadoc listFromCookie
     * @apiNote 从cookie中查询某键对应的值列表
     *
     * @param exchange 请求
     * @param key cookie 键
     * @return java.util.List<java.lang.String>
     * @author zhang yebai
     * @date 2021/6/9 4:30 PM
     **/
    public static @NotNull List<String> listFromCookie(ServerWebExchange exchange, String key){
        final var cookies = exchange.getRequest().getCookies();
        final var cookieList = cookies.get(key);
        if(CollectionUtils.isNotEmpty(cookieList)){
            return cookieList.stream().map(HttpCookie::getValue).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    /**
     * javadoc rebuildRequest
     * @apiNote 重构请求并在header中放入token荷载
     *
     * @param exchange 请求
     * @param payload token
     * @return org.springframework.http.server.reactive.ServerHttpRequestDecorator
     * @author zhang yebai
     * @date 2021/6/9 4:29 PM
     **/
    public static ServerHttpRequestDecorator rebuildRequest(ServerWebExchange exchange, TokenPo payload){
        final HttpHeaders headers = exchange.getRequest().getHeaders();
        headers.add("token", JsonUtil.toJsonString(payload));
        return new CustomizedServerHttpRequestDecorator(exchange.getRequest(), headers);
    }
}
