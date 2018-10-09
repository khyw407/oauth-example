package com.test.oauthserver.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class TokenStoreConfiguration {
	//디폴트가 inMemory인데 h2 DB에 토큰을 저장하기 위해서 추가하였음
	/*
     * Input Type	: DataSource
     * Output Type	: TokenStore
     * Description	: 
     */
	
	@Bean
    public TokenStore jdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }
    
}
