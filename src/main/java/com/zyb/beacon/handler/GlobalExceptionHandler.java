package com.zyb.beacon.handler;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * javadoc 全局统一异常处理器
 *
 * @author zhang yebai
 * @date 2019-07-27 14:20
 * @version 1.0.0
 */
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {
    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     * @param errorAttributes the error attributes
     * @param resources the resources configuration properties
     * @param errorProperties the error configuration properties
     * @param applicationContext the current application context
     * @since 2.4.0
     */
    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                           ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        final Map<String, Object> result = Maps.newHashMap();
        result.put("data", null);
        Throwable error = super.getError(request);

        log.error("request[{}], cookies=[{}], headers=[{}] error: ",request.path(), request.cookies(), request.headers(), error);
        if (error instanceof ResponseStatusException){
            ResponseStatusException e = (ResponseStatusException) error;
            if (e.getStatus().value() == HttpStatus.NOT_FOUND.value()){
                result.put("code", HttpStatus.NOT_FOUND.value());
                result.put("message", "no router handler found for this request");
                return result;
            }
        }
        result.put("code", 500);
        result.put("message", "Customized Internal Server Error");
        return result;
    }

//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
//        final Map<String, Object> result = Maps.newHashMap();
//        result.put("data", null);
//        Throwable error = super.getError(request);
//
//        log.error("request[{}], cookies=[{}], headers=[{}] error: ",request.path(), request.cookies(), request.headers(), error);
//        //NotFoundException){
//        if (error instanceof ResponseStatusException){
//            ResponseStatusException e = (ResponseStatusException) error;
//            if (e.getStatus().value() == HttpStatus.NOT_FOUND.value()){
//                result.put("code", 404);
//                result.put("message", "no router handler found for this request");
//                return result;
//            }
//        }
//        result.put("code", 500);
//        result.put("message", "Customized Internal Server Error");
//        return result;
//    }

    // 永远都返回200

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.OK.value();
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }
}
