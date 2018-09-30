package com.test.oauthserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfiguration extends AuthorizationServerConfigurerAdapter{
	
	private final AuthenticationManager authenticationManager;
	
    private final ClientDetailsService clientDetailsService;
	
    @Autowired
    private TokenStore tokenStore;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    public OauthServerConfiguration(AuthenticationManager authenticationManager,
    								ClientDetailsService clientDetailsService) {
    	this.authenticationManager = authenticationManager;
    	this.clientDetailsService = clientDetailsService;
    }

	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
			.authenticationManager(this.authenticationManager);
	}
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.withClientDetails(this.clientDetailsService);
		
		clients.jdbc(dataSource);
		/*inMemory()
		 .withClient("client1")
         .authorizedGrantTypes("client_credentials", "password")
         .authorities("ROLE_CLIENT")
         .scopes("read")
         .resourceIds("oauth2-resource")
         .secret("test1");
         */
	}	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception{
		oauthServer.checkTokenAccess("isAuthenticated()");
	}
}