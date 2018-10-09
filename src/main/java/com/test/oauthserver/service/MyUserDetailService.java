package com.test.oauthserver.service;

import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.test.oauthserver.domain.Account;

@Component
public class MyUserDetailService implements UserDetailsService{
	
	private RestTemplate restTemplate = new RestTemplate();
	
	/*
	 * Input Type	: String
	 * Output Type	: UserDetails
	 * Description	: username를 통해 User 서버에서 검색하여 HttpEntity로 리턴받는다.
	 * 				  Account 클래스가 UserDetails 인터페이스의 구현체이기 때문에 HttpEntity로 리턴된
	 * 				    데이터의 Body를 리턴한다.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HttpEntity<Account> entity = restTemplate.getForEntity("http://localhost:8082/account/user1", Account.class);		
		
		return entity.getBody();
	}

}
