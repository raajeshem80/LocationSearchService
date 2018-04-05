package com.usersearchservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class UserSearchServiceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSearchServiceApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(UserSearchServiceApplication.class, args);
	}

}