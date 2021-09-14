package com.mywarehouse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication

public class DemoApplication {

	@Value("${application.allowed-origins:https://some.com,https://other.com}")
	String[] allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
		var result = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());

		result.setName("etagFilter");
		return result;
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsFilter() {
		var source = new UrlBasedCorsConfigurationSource();
		var config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList(allowedOrigins));
		config.addAllowedHeader("*");
		config.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT"));
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
