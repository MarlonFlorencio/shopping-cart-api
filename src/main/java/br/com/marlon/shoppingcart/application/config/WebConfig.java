package br.com.marlon.shoppingcart.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	public void addCorsMappings(CorsRegistry registry) {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		registry
			.addMapping("/**")
			.allowedMethods("*")
			.allowedOrigins("*")
			.allowedHeaders("*")
			.allowCredentials(true);
	}




}
