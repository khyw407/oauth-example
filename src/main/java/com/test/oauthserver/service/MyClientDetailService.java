package com.test.oauthserver.service;

import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.test.oauthserver.domain.Client;

@Component
public class MyClientDetailService implements ClientDetailsService{
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		HttpEntity<Client> entity = restTemplate.getForEntity("http://localhost:8083/client/client1", Client.class);
		BaseClientDetails baseClientDetails = new BaseClientDetails(entity.getBody().getClientId(), entity.getBody().getResourceIds(), entity.getBody().getScope()
																	, entity.getBody().getAuthorizedGrantTypes(), entity.getBody().getAuthorities(), "");
		
		baseClientDetails.setClientSecret(entity.getBody().getClientSecret());
		
		return baseClientDetails;
	}
}
