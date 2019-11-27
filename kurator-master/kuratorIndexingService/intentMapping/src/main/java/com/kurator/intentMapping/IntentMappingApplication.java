package com.kurator.intentMapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.kurator.library", "com.kurator.intentMapping"})
@EnableDiscoveryClient
@EnableFeignClients("com.kurator.intentMapping")
public class IntentMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntentMappingApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
