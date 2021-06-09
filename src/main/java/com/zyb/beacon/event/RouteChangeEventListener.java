package com.zyb.beacon.event;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * javadoc RouteContextRefresher
 * <p>
 *     路由上下文刷新
 * <p>
 * @author zhang yebai
 * @date 2021/6/9 2:08 PM
 * @version 1.0.0
 **/
@SuppressWarnings(value = "all")
@Component
@Slf4j
public class RouteChangeEventListener implements ApplicationEventPublisherAware, ApplicationContextAware {

    //private static final String ROUTE_ID = "spring\\.cloud\\.gateway\\.routes\\[\\d+\\]\\.id";

    //private static final String ROUTE_FILTER = "spring\\.cloud\\.gateway\\.default-filters\\[\\d+\\]\\.name";

    private ApplicationContext applicationContext;

    private ApplicationEventPublisher applicationEventPublisher;


    private GatewayProperties gatewayProperties;
    @Autowired
    public void setGatewayProperties(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * javadoc onChange
     * @apiNote interestedKeyPrefixes 需要监听的key前缀
     *          value namespace
     *
     * @param event
     * @return void
     * @author zhang yebai
     * @date 2021/6/9 2:46 PM
     **/
    @ApolloConfigChangeListener(interestedKeyPrefixes = GatewayProperties.PREFIX, value = "application")
    public void onChange(ConfigChangeEvent event){
        log.error("before refresh, routes : [{}]", gatewayProperties);
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        log.error("after refresh, routes : [{}]", gatewayProperties);
    }
}
