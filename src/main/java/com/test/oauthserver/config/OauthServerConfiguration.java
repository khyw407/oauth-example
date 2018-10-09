package com.test.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.test.oauthserver.service.MyClientDetailService;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfiguration extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager; //인증을 담당하는 역할, 권한 등을 설정할 수 있는 객체
	
	@Autowired
    private MyClientDetailService myClientDetailService;
	
    @Autowired
    private TokenStore tokenStore;
    
    /*
     * Input Type	: AuthorizationServerEndpointsConfigurer
     * Output Type	: void
     * Description	: OAuth2 서버가 작동하기 위한 endpoint에 대한 정보를 설정
     * 				    토큰 발급 및 저장에 대한 설정과 Spring Security와 OAuth를 연결한다.
     */
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
			.authenticationManager(this.authenticationManager);
	}
	
	 /*
     * Input Type	: ClientDetailsServiceConfigurer
     * Output Type	: void
     * Description	: API를 요청하는 클라이언트 정보를 설정한다. 
     * 				  Client 서버(http://localhost:8083)로부터 클라이언트에 대한 정보를 가져와서 설정한다.
     */
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(this.myClientDetailService);
	}
	
	 /*
     * Input Type	: AuthorizationServerSecurityConfigurer
     * Output Type	: void
     * Description	: Oauth 서버에서 발급한 access token을 다른 API 서버에서 사용할 때
     * 				    해당 토큰이 사용 가능한지를 체크할 수 있는 것을 허용
     */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception{
		oauthServer.checkTokenAccess("isAuthenticated()");
	}
	
}