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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HttpEntity<Account> entity = restTemplate.getForEntity("http://localhost:8082/account/user1", Account.class);		
		
		return entity.getBody();
	}

}
