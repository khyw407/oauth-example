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
		return "hello oauth server";
	}
}

/*
 *프로젝트 생성시 자동으로 만들어지는 클래스이다. 스프링부트는 내장 톰캣을 지원하여 따로 서버를 돌릴 필요 없이
 *자바 어플리케이션을 실행시키는 것처럼 
 */