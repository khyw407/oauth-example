package com.test.oauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class OauthserverApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OauthserverApplication.class, args);
	}
	
	@RequestMapping("/oauth")
	public String test() {
		return "hello oauth";
	}
}