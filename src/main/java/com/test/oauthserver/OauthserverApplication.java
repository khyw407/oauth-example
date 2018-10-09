package com.test.oauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Description	: @SpringBootApplication은 스프링 부트 애플리케이션임을 나타내는 어노테이션이다.
 * 				    해당 어노테이션은 @Configuration, @EnableAutoConfiguration, @ComponentScan을 묶은 것이다.
 * 				  SpringApplicaion.run 메소드를 통해 스프링부트 애플리케이션을 실행시킨다. 
 */
@SpringBootApplication
public class OauthserverApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OauthserverApplication.class, args);
	}
	
	/*
	 * Output Type	: String
	 * Description	: @RequestMapping 어노테이션을 통해 /oauth 경로의 요청을 test()메소드에서 처리하도록 한다.
	 */
	@RequestMapping("/oauth")
	public String test() {
		return "hello oauth server";
	}
}
