package com.zyb.beacon;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * javadoc BeaconApplication
 * <p>
 *     dynamic spring gateway
 *     beacon shine
 * <p>
 * @author zhang yebai
 * @date 2021/6/8 7:23 PM
 * @version 1.0.0
 **/
@EnableApolloConfig
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class BeaconApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconApplication.class, args);
	}

}
