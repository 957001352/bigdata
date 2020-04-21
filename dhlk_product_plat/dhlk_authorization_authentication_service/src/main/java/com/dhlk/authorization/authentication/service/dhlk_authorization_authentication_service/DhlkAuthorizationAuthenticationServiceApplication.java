package com.dhlk.authorization.authentication.service.dhlk_authorization_authentication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan("com.dhlk.authorization.authentication.service")
@SpringBootApplication
public class DhlkAuthorizationAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DhlkAuthorizationAuthenticationServiceApplication.class, args);
	}

}
