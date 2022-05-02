package com.ssafy.authsvr;

import com.ssafy.authsvr.oauth.config.properties.AppProperties;
import com.ssafy.authsvr.oauth.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
