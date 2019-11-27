package com.kurator.semanticService.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.kurator.semanticService.controller"))
				.paths(regex("/.*"))
				.build().apiInfo(metaData());

	}
	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo(
				"Kurator Spring Boot REST API",
				"Spring Boot REST API for Kurator Platform",
				"1.0",
				"Terms of Service",
				new Contact("Team Kurator", "https://localhost:8097/", ""),
				"Kurator Platform License",
				"https://localhost:8097/");
		return apiInfo;
	}
}


