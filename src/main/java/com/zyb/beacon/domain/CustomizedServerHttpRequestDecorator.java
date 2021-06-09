package com.zyb.beacon.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.lang.NonNull;

/**
 * javadoc rebuild request
 *
 * @author zhang yebai
 * @date 2020/1/6 19:47
 * @version 1.0.0
 */
public class CustomizedServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private final HttpHeaders refactorHeaders;
    public CustomizedServerHttpRequestDecorator(ServerHttpRequest delegate, HttpHeaders headers) {
        super(delegate);
        this.refactorHeaders = headers;
    }

    @Override
    @NonNull
    public HttpHeaders getHeaders() {
        return this.refactorHeaders;
    }
}
