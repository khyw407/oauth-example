# <oauth-example>

# 1. OauthserverApplication.java
<img width="531" alt="oauthserverapplication" src="https://user-images.githubusercontent.com/37721713/46654432-c81a9480-cbe3-11e8-9f8f-44f36df075a2.PNG">

*@SpringBootApplication 

    -스프링부트 애플리케이션임을 나타내는 어노테이션을 의미


    -@Configuration, @EnableAutoConfiguration, @ComponentScan을 포함하는 의미를 갖는다. 


        @Configuration - 스프링의 자바 기반 구성 클래스를 지정한다. (스프링 컨테이너가 해당 클래스를 빈 정의의 소스로 사용한다는 의미)
                                             
                                             
        @EnableAutoConfiguration - classpath를 기반으로 ApplicationContext가 생성될 수 있도록 하는 역할.
                            
                            
        @ComponentScan - 메인클래스가 위치한 root package를 기본 속성으로 사용하여 자동으로 다른 컴포넌트 클래스들을 검색하여 빈으로 등록
                                             
                                             

*SpringApplication.run

    -스프링부트 애플리케이션을 실행시키는 메소드

# 2. OauthServerConfiguration.java
<img width="528" alt="oauthserverconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654433-c8b32b00-cbe3-11e8-9f35-8f0a5df2b029.PNG">


<img width="529" alt="oauthserverconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654434-c8b32b00-cbe3-11e8-83cd-9a4d44852034.PNG">


*@Autowired

    - @Autowired는 의존관계자동설정 어노테이션을 의미한다. Spring 설정 파일을 보고 자동으로 속성의 set 메서드에 해당하는 역할을 수행한다.
    
    
*@Configuration

    - 스프링의 자바 기반 구성 클래스를 지정한다. (스프링 컨테이너가 해당 클래스를 빈 정의의 소스로 사용한다는 의미)
    
    
*@EnableAuthorizationServer

    - OAuth2의 인증서버를 구성하는 어노테이션, 해당 어노테이션 설정만으로도 인증서버의 역할을 수행한다.
    
    
*AuthorizationServerConfigurerAdapter

    - AuthorizationServerConfigurerAdapter는 AuthorizationServerConfigurer 인터페이스를 구현한다.
      AuthorizationServerConfigurer는 스프링 시큐리티 OAuth가 초기화 단계에서 호출하는 콜백 인터페이스로 configure 메소드를
      오버라이드하여 서비스의 동작을 바꿀 수 있다.
        
    

# 3. TokenStoreConfiguration.java
<img width="547" alt="tokenstoreconfiguration" src="https://user-images.githubusercontent.com/37721713/46654435-c8b32b00-cbe3-11e8-8b16-045648ce9d81.PNG">


# 4. MyUserDetailServiceConfiguration.java
<img width="595" alt="myuserdetailserviceconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654430-c81a9480-cbe3-11e8-9ce6-8cf9f28d4668.PNG">


<img width="505" alt="myuserdetailserviceconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654431-c81a9480-cbe3-11e8-982f-57d09d427e29.PNG">


*WebSecurityConfigurerAdapter

    - 웹 기반 보안 기능을 제공하는 클래스이며 URL에 대한 인증, 사용자 생성 등이 가능


# 5. MyClientDetailService.java
<img width="643" alt="myclientdetailservice" src="https://user-images.githubusercontent.com/37721713/46654427-c781fe00-cbe3-11e8-81cb-b767a554f03e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


# 6. MyUserDetailService.java
<img width="649" alt="myuserdetailservice" src="https://user-images.githubusercontent.com/37721713/46654429-c781fe00-cbe3-11e8-85c4-6b27d28d8d7e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


# 7. Account.java
<img width="377" alt="account1" src="https://user-images.githubusercontent.com/37721713/46654423-c6e96780-cbe3-11e8-812f-d5085b4ccd39.PNG">


<img width="263" alt="account2" src="https://user-images.githubusercontent.com/37721713/46654424-c6e96780-cbe3-11e8-894e-75e8f83ae3ee.PNG">


*@Data

    - @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 모두 포함하는 어노테이션


*@NoArgsConstructor

    - 파라미터가 없는 기본 생성자를 만들어주는 어노테이션
    
    
*@AllArgsConstructor

    - 모든 필드 값을 파라미터로 받는 생성자를 만들어 주는 어노테이션


*UserDetails

    - 스프링 시큐리티에서 사용되는 유저의 세부정보 관련 인터페이스


# 8. Client.java
<img width="276" alt="client" src="https://user-images.githubusercontent.com/37721713/46654426-c781fe00-cbe3-11e8-92f9-8c43e72d96d6.PNG">


*@Data

    - @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 모두 포함하는 어노테이션


*@NoArgsConstructor

    - 파라미터가 없는 기본 생성자를 만들어주는 어노테이션
    
    
*@AllArgsConstructor

    - 모든 필드 값을 파라미터로 받는 생성자를 만들어 주는 어노테이션


# 9. application.yml
<img width="219" alt="application" src="https://user-images.githubusercontent.com/37721713/46654425-c6e96780-cbe3-11e8-9b99-d014fde14ad7.PNG">  


*logging:level:org.springframework.security

    - org.springframework.security에만 로그 레벨을 디버그로 지정
