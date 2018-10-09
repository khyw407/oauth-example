package com.test.oauthserver.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class TokenStoreConfiguration {
	
	/*
     * Input Type	: DataSource
     * Output Type	: TokenStore
     * Description	: 토큰을 저장하기 위한 곳이 TokenStore이다. 
     *                TokenStore는 기본적으로 자바 내부의 메모리, JDBC를 사용한 DB, JWT, Redis에 저장하는 방식을 지원한다.
     *                H2 DB를 사용해 토큰을 저장할 수 있도록 JdbcTokenStore를 사용함.
     */
	
	@Bean
    public TokenStore jdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }
    
}
