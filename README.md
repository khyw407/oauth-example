# <oauth-example>


# 0. OAuth란?


*OAuth 2.0

    - OAuth 2.0은 인증 및 권한부여를 관리하는 범용 프레임워크
      OAuth 기반 서비스의 API를 호출을 할 때에는, HTTP 헤더에 access token을 포함하여 요청을 보내며, 
      서비스는 access token을 검사하면서 이 요청이 유효한지 판단하여 적절한 결과를 응답합니다.
      (사용자 입장에서는 access token 획득이 중요, 서비스 입장에서는 access token 발급이 중요)
      
      
*OAuth 2.0의 Roles

    - Resource Owner(자원 소유자)는 protected resource(보호된 자원)에 접근하는 권한을 제공
    
    - Resource server(자원 서버)는 access token을 사용해서 요청을 받을 때, 권한을 검증한 후 적절한 결과를 응답
    
    - Client(클라이언트)는 Resource Owner(자원 소유자)의 protected resource에 접근을 요청을 하는 애플리케이션
    
    - Authorization Server(권한 서버)는 Client가 성공적으로 access token을 발급받은 이후에 Resource Owner를 인증하고 권한부여
    
    
*OAuth 2.o의 Resource Owner Password Credentials Grant 방식 예시(테스트를 해당 방식으로 진행하였음)
<img width="482" alt="oauth resource owner password credentilas grant" src="https://user-images.githubusercontent.com/37721713/46660227-8c86c700-cbf1-11e8-8e75-87e76fffb26b.PNG">


*Access Token 획득 테스트 예시(Postman 사용)
<img width="769" alt="oauthserver_test" src="https://user-images.githubusercontent.com/37721713/46660649-72011d80-cbf2-11e8-9c4e-9099531b300d.PNG">


*Access Token을 가지고 Api서버에 요청 테스트(Postman 사용)
<img width="763" alt="apiserver_test" src="https://user-images.githubusercontent.com/37721713/46660647-71688700-cbf2-11e8-932e-41e4cc5f1495.PNG">



# 1. OauthserverApplication.java
<img width="550" alt="oauthserverapplication" src="https://user-images.githubusercontent.com/37721713/46654432-c81a9480-cbe3-11e8-9f8f-44f36df075a2.PNG">

*@SpringBootApplication 

    -스프링부트 애플리케이션임을 나타내는 어노테이션을 의미


    -@Configuration, @EnableAutoConfiguration, @ComponentScan을 포함하는 의미를 갖는다. 


        @Configuration - 스프링의 자바 기반 구성 클래스를 지정한다. (스프링 컨테이너가 해당 클래스를 빈 정의의 소스로 사용한다는 의미)
                                             
                                             
        @EnableAutoConfiguration - classpath를 기반으로 ApplicationContext가 생성될 수 있도록 하는 역할.
                            
                            
        @ComponentScan - 메인클래스가 위치한 root package를 기본 속성으로 사용하여 자동으로 다른 컴포넌트 클래스들을 검색하여 빈으로 등록
                                             
                                             

*SpringApplication.run

    -스프링부트 애플리케이션을 실행시키는 메소드

# 2. OauthServerConfiguration.java
<img width="550" alt="oauthserverconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654433-c8b32b00-cbe3-11e8-9f35-8f0a5df2b029.PNG">


<img width="550" alt="oauthserverconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654434-c8b32b00-cbe3-11e8-83cd-9a4d44852034.PNG">


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
<img width="550" alt="tokenstoreconfiguration" src="https://user-images.githubusercontent.com/37721713/46654435-c8b32b00-cbe3-11e8-8b16-045648ce9d81.PNG">


# 4. MyUserDetailServiceConfiguration.java
<img width="550" alt="myuserdetailserviceconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654430-c81a9480-cbe3-11e8-9ce6-8cf9f28d4668.PNG">


<img width="550" alt="myuserdetailserviceconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654431-c81a9480-cbe3-11e8-982f-57d09d427e29.PNG">


*WebSecurityConfigurerAdapter

    - 웹 기반 보안 기능을 제공하는 클래스이며 URL에 대한 인증, 사용자 생성 등이 가능


# 5. MyClientDetailService.java
<img width="550" alt="myclientdetailservice" src="https://user-images.githubusercontent.com/37721713/46654427-c781fe00-cbe3-11e8-81cb-b767a554f03e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


# 6. MyUserDetailService.java
<img width="550" alt="myuserdetailservice" src="https://user-images.githubusercontent.com/37721713/46654429-c781fe00-cbe3-11e8-85c4-6b27d28d8d7e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


# 7. Account.java
<img width="550" alt="account1" src="https://user-images.githubusercontent.com/37721713/46654423-c6e96780-cbe3-11e8-812f-d5085b4ccd39.PNG">


<img width="550" alt="account2" src="https://user-images.githubusercontent.com/37721713/46654424-c6e96780-cbe3-11e8-894e-75e8f83ae3ee.PNG">


*@Data

    - @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 모두 포함하는 어노테이션


*@NoArgsConstructor

    - 파라미터가 없는 기본 생성자를 만들어주는 어노테이션
    
    
*@AllArgsConstructor

    - 모든 필드 값을 파라미터로 받는 생성자를 만들어 주는 어노테이션


*UserDetails

    - 스프링 시큐리티에서 사용되는 유저의 세부정보 관련 인터페이스


# 8. Client.java
<img width="550" alt="client" src="https://user-images.githubusercontent.com/37721713/46654426-c781fe00-cbe3-11e8-92f9-8c43e72d96d6.PNG">


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
