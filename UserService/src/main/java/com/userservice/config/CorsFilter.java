package com.userservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("com.userservice")
@EnableWebMvc
public class CorsFilter extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/v1/api/**")
	   	  .allowedOrigins("http://localhost:8402", "http://localhost:8100")
		  .allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
		  .allowedHeaders("X-Auth-Token", "Content-Type")
		  .exposedHeaders("custom-header1", "custom-header2")
		  .allowCredentials(false)
		  .maxAge(4800);
	}
}
