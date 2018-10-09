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
@EnableAuthorizationServer //Oauth2 인가 서버
public class OauthServerConfiguration extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager; //인증을 담당하는 역할, 권한 등을 설정할 수 있는 객체
	
	@Autowired
    private MyClientDetailService myClientDetailService;
	
    @Autowired
    private TokenStore tokenStore;
    
    //Oauth 서버가 동작하기 위한 endpoint 정보를 설정
    //토큰 발급 및 저장에 대한 설정 + 스프링시큐리티와 Oauth를 연결
    /*
     * Input Type	: AuthorizationServerEndpointsConfigurer
     * Output Type	: void
     * Description	: 
     */
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
			.authenticationManager(this.authenticationManager);
	}
	
	//클라이언트에 대한 정보를 가져오는 부분
	//현재 DB에 클라이언트 정보를 INSERT 해놨기 때문에
	//서버가 구동되면서 H2 DB에 자동으로 넣어놨음.
	 /*
     * Input Type	: ClientDetailsServiceConfigurer
     * Output Type	: void
     * Description	: 클라이언트에 대한 정보를 DB (H2 DB)에서 가져온다.
     * 				  schema.sql, data.sql을 통해 DB에 데이터를 INSERT 했고, OAuth 서버 구동시 데이터가 들어감.
     */
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(this.myClientDetailService);
	}
	
	// Oauth 서버에서 발급한 access token을  API서버가 사용하면서 해당 토큰이 
	// 사용가능한지를 체크하는 부분
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