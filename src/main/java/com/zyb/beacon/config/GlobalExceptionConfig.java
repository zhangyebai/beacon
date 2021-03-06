package com.zyb.beacon.config;

import com.zyb.beacon.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * javadoc 代替reactive web原有的异常处理器, 进行全局异常处理
 * 
 * @author zhang yebai
 * @date 2020/1/3 16:40
 * @version 1.0.0 
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class, WebProperties.class})
public class GlobalExceptionConfig {

    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final WebProperties resourceProperties;
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GlobalExceptionConfig(ServerProperties serverProperties, WebProperties resourceProperties,
                                 ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer,
                                 ApplicationContext applicationContext) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties;
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        final var globalExceptionHandler = new GlobalExceptionHandler(
                errorAttributes,
                this.resourceProperties.getResources(),
                this.serverProperties.getError(),
                this.applicationContext
        );
        globalExceptionHandler.setViewResolvers(this.viewResolvers);
        globalExceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        globalExceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return globalExceptionHandler;
    }
}
