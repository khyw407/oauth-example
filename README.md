# <oauth-example>

# 0. OAuth 동작 메커니즘

1. 용어정리

    - Resource Owner : 사용자(김현우, 홍길동….), Resource Server에 저장되어 있는 정보의 주인을 의미

    - Client : 우리가 만들고자 하는 web, app 등 (3rd party 솔루션 등이 해당됨)

    - Resource Server : 사용자들이 가지고 있는 데이터(Resource)를 보관하는 주체

    - Authorization Server : Authorization을 획득한 Client에게 access token을 발급

    *OAuth는 표준화된 기술이며 수많은 Resource Server이 폭넓게 채택하고 있다. Authorization Server와 Resource Server를 한 곳에서 같이 사용하는 경우가 많음.


2. 과거 전통적인 방식(OAuth 개념 적용 이전)
<img width="399" alt="beforeoauth" src="https://user-images.githubusercontent.com/37721713/47211995-fb260a80-d3d1-11e8-90cd-f1ff07ae4ff3.PNG">

    Resource Owner가 Client에 접속하면 ID와 Password를 요청한다

    -> Resource Owner가 해당 정보를 전달해주면 Client는 이것을 보관하고, Resource Server에 접근할 때 사용

    -> 해당 Resource Server는 Resource Owner가 접속했다고 판단하고 데이터를 제공하지만, 실제로는 Client가 이것을 받아간다.


*장점 : 구현이 OAuth에 비해 매우 쉽고, 모든 데이터를 요청할 수 있음.


*단점 : 보안의 문제 발생, 예를 들어 Resource Server에 매우 중요한 기밀 데이터가 있고 Resource Owner는 자신의 ID와 Password를 사용해서 수많은 서비스를 접근한다면 Client에게 자신의 ID, Password를 주는 것은 매우 위험.


3. OAuth 적용 방식(Google 서비스 예시- Authorization Code방식)
<img width="450" alt="wholeprocess" src="https://user-images.githubusercontent.com/37721713/47208241-3e7b7b80-d3c8-11e8-9a5b-a513593812a9.PNG">


    Client는 우리의 서비스(Client를 의미)가 당신들(Resource Server)의 서비스를 사용한다는 의미의 등록 과정을 우선적으로 수행한다.
    
    -> Resource Server는 나중에 Client들을 식별할 수 있게 Client ID와 Client Secret을 발급해 준다. (Client는 이 두 값을 저장해 놓아야 하며, 특히 Client Secret 값은 절대 노출되면 안된다.)
    
    -> Resource Owner가 Client에 접속을 하면, 우리의 서비스(Client)가 Resource Server의 데이터를 사용할 수 있도록 허가해 달라는 인증요청이 나온다.
    
    -> Resource Owner가 허가를 하면 Resource Server에 접속하게 된다. 그리고 해당 Client가 Resource Server에 접속하는 것을 허용하게 된다.
    
    -> Resource Server는 Client에게 authorization code를 준다. 이것은 Resource Owner가 Client에게 Resource Server의 정보를 제공하도록 승인했다는 의미를 담는다.
    
    *authorization code만으로는 Resource Server에 접근할 수는 없다.
    
    -> Client는 authorization code, Client ID, Client Secret을 담아서 Resource Server에 다시 보낸다.
    
    -> Resource Server는 요청된 값들을 비교해서 권한을 달라고 하는 Client가 Resource Owner가 승인한 것인지를 검증한다. 
    
    -> 검증결과 모두 유효한 값이라면 Client에게 access token을 발급한다.
    
    -> Client는 access token을 저장(DB 또는 InMemory 등)해서 이것을 바탕으로 Resource Server에 접속 하게 된다. (보통 HTTP 헤더에 해당 토큰 정보를 넣어서 사용)
    
    -> Resource Server는 access token 값을 보고 자신이 발급한 것이라면 이것을 바탕으로 데이터를 Client에게 제공한다. Client는 이 데이터를 가공하여 Resource Owner에게 가치를 제공한다.


*authorization code, access token을 Resource Server에서 넘겨줄 때 redirect_url을 설정해야 하는데 두 경우의 redirect_url이 동일해야 한다. 만약 다르거나 값이 없으면 401 에러(Unauthorized)가 발생한다. 정상동작을 위해서는 꼭 같은 값을 가져야 함!!!!


*보통 이런 일련의 인증이 복잡하여 구글, 페이스북 등에서는 SDK를 제공하고 있다.


# 1. OAuth란

1) OAuth의 개념

    OAuth는 토큰 기반 Authorization의 표준이다. 토큰은 ID와 Password가 노출되는 시간 간격을 줄이고, Client가 패스워드에 얽매이지 않게 하면서도 잘못된 Client의 접근을 막는다. 
    
    *OpenID의 주요목적은 인증(Authentication)이지만, OAuth의 주요목적은 허가(Authorization)이다. OpenID를 사용하는 여러 서비스는 OpenID Provider에게 인증을 위임한다. OAuth도 인증하는 절차를 두고 인증을 할 수 있지만, 근본적인 목적은 API를 호출하는 것이다.
    
    (두 방식 모두 HTTP를 사용하며, 표준 프로토콜이다.)

    *OAuth는 인증이 아닌 허가 프로토콜, 인증으로 따로 처리해야 한다. OAuth는 Client에게 서비스에서 제공되는 Resource Owner를 대신해서 Resource Server의 서비스에 접근할 수 있도록 한다.

2) OAuth의 등장 배경

    3rd party 솔루션에 Resource Owner의 ID와 Password를 제공하면 보안상 취약하다. 
    
    즉, 각종 어플리케이션에 ID/PW를 제공하면 접근범위가 점차 늘어나고 수많은 어플리케이션들이 Resource Owner의 정보를 활용할 수 있게 된다. 
    
    또한 Password를 변경하게 되면 기존에 사용하던 어플리케이션들은 동작하지 못한다.

3) OAuth 1.0의 등장

    크게 User, Consumer, Service Provider로 이루어지는 Authorization 과정을 거친다.

*용어설명


<img width="525" alt="oauth1 0a" src="https://user-images.githubusercontent.com/37721713/47208234-3de2e500-d3c8-11e8-80d4-6c8b094494ab.PNG">

*프로세스


<img width="500" alt="oauth1 0aflow" src="https://user-images.githubusercontent.com/37721713/47208235-3de2e500-d3c8-11e8-947b-05e916ef2857.PNG">


    Request token의 요청과 발급
    
    -> 사용자 인증페이지 호출 -> 사용자 로그인 완료
    
    -> 사용자의 권한 요청 및 수락 -> access token 발급
    
    -> access token을 이용해 서비스 정보 요청

    *OAuth 1.0a의 문제점 : 웹이 아닌 어플리케이션에서는 사용하기 어렵고, HMAC을 통해 암호화를 하는 등의 구현이 복잡하였다. 또한 access token이 만료되지 않았다.


4) OAuth 2.0과 OAuth 1.0a의 차이점


    - 프로세스의 단순화(확장성에 유리함).
    
    - 1.0a는 만들어진 이후 표준이 되었지만 2.0은 처음부터 표준 프로세스로 만들어짐.
    
    - https가 필수이고, https가 암호화를 담당.(HMAC을 사용하지 않음)
    
    - 1.0a 비해 다양한 인증방식을 제공.
    
    - access token의 만료시간 설정이 가능.
    
    - 2,0에서는 Resource Server와 Authorization Server를 분리할 수 있음.


5) OAuth 2.0의 인증방식

    OAuth 2.0의 인증방식은 크게 4가지이며 Authorization Code Grant, Implicit Grant, Resource Owner Password Credentials Grant, Client Credentials Grant로 구성된다.
    
    
    또한 다음과 같이 타입을 나눌 수도 있다.

<img width="500" alt="oauth2 0 category" src="https://user-images.githubusercontent.com/37721713/47208236-3de2e500-d3c8-11e8-8925-ba26b8b2065f.PNG">


    * 3-legged : 3-legged OAuth는 OAuth의 가장 일반적인 구현 시나리오이다. 
    
    Resource Owner가 Client를 Resource Server에 접근 가능하도록 할 때 ID와 Password를 공유하지 않도록 하는 것을 의미한다. 
    
    예를 들면, 3rd party 솔루션이 Resource Server의 구글 캘린더 서비스를 사용해야 하는 경우 Resource Owner의 구글 계정으로 로그인해 허가를 하는 것이 대표적이다.

    * 2-legged : Resource Owner가 개입하지 않는 클라이언트-서버 시나리오에 일반적으로 적용된다. 
    
    즉, Client가 사전에 권한이 부여되어 있어 Resource Owner와의 상호작용이 필요 없음을 의미한다.


- Authorization Code Grant

<img width="478" alt="authorization code grant flow" src="https://user-images.githubusercontent.com/37721713/47208228-3cb1b800-d3c8-11e8-9abe-a6d87c2ea360.PNG">

    서버사이드 코드로 인증하는 방식.
    
    Authorization Server가 Client와 Resource Server의 중재역할.
    
    Access token을 바로 Client로 전달하지 않아 잠재적 유출을 방지.
    
    로그인시에 페이지 URL에 response_type=code라고 넘긴다.

- Implicit Grant

<img width="493" alt="implicit grant flow" src="https://user-images.githubusercontent.com/37721713/47208233-3d4a4e80-d3c8-11e8-85cc-b872bb3b1cb2.PNG">

    Token과 scope에 대한 스펙은 다르나 OAuth 1.0a와 가장 유사한 방식.
    
    Public Client인 브라우저 기반의 어플리케이션이나 모바일 등에서 주로 사용
    
    OAuth 2.0에서 가장 많이 사용되는 방식
    
    주로 read only인 서비스에 사용
    
    로그인시에 URL에 response_type=token라고 하고 넘긴다.


- Resource Owner Password Credentials Grant

<img width="461" alt="resource owner password credentials grant flow" src="https://user-images.githubusercontent.com/37721713/47208237-3e7b7b80-d3c8-11e8-80f3-8856cc46ae3a.PNG">

    Client에 Resource Owner의 ID/Password를 저장해 놓고, 이것을 이용해 직접 access token을 받아오는 방식.
    
    Client를 믿을 수 없을 때는 매우 위험하기 때문에 API 서비스의 공식 어플리케이션이나 믿을 수 있는 Client에 한해서만 사용해야 함
    
    로그인시에 API에 POST로 grant_type=password라고 넘긴다.
    
    

- Client Credentials Grant

<img width="453" alt="client credentials grant" src="https://user-images.githubusercontent.com/37721713/47208231-3d4a4e80-d3c8-11e8-99e6-79f8f245af69.PNG">

    어플리케이션이 Confidential Client일 때 Client의 ID와 Secret을 가지고 인증하는 방식.
    
    로그인 시에 API에 POST로 grant_type=client_credentials라고 넘긴다.


6) 일반적인 OAuth의 프로세스(구글과 같은 서비스에서는 일반적으로 Authorization Code방식을 사용한다.)

    Resource Owner가 Client(우리가 만든 서비스)에 접속
    
    -> Client에게 인증요청에 대한 화면(사용자의 동의)이 나온다.
    
    -> 인증 버튼을 누르면 해당 링크가 Resource Server를 가리키고 있으므로, Resource Owner는 Resource Server에 접속.
    
    -> Resource Server에서 Resource Owner에게 로그인이 안된 경우 로그인 요청 또는 해당 Client가 필요로 하는 scope를 확인할 수 있도록 한다.
    
    -> Resource Owner가 허가하면, Resource Server가 Client에게 authorization code(Resource Owner가 해당 Client에게 Resource Server에 있는, 정보를 사용할 수 있게 하겠다는 허락에 대한 의미)를 전송
    
    -> Resource Server가 전달한 authorization code와 Client ID, Client Secret을 하나로 묶어서 Resource Server에 다시 전달.
    
    -> Resource Server는 확인절차를 거쳐서 Client에게 access token을 발급.
    
    -> Client는 access token을 이용해서 Resource Server에서 데이터를 가져올 수 있음.(즉 API 사용 가능)


    
    *Client를 Resource Server에 등록을 완료하고 Client ID와 Client Secret 정보를 가지고 있다고 가정한다.
    
    *scope : Resource Server(구글, 페이스북 등)의 어떤 서비스에 접근할 권한이 무엇인지를 Resource Owner와 Resource Server 모두에게 알려주기 위해 지정하는 것을 의미.


7) authorization code 획득 시 파라미터 설명

    -scope : Resource Server(구글, 페이스북 등)의 어떤 서비스에 접근할 권한이 무엇인지를 Resource Owner와 Resource Server 모두에게 알려주기 위해 지정하는 것을 의미.

    -access_type : access token을 Client가 계속 가지고 있으면, 유출 등의 위험이 있으므로 이를 관리하기 위한 방법. (Resource Owner에게 허가를 받는 것이 부담이기 때문에 2가지 타입이 존재)

        online – refresh token을 발급하지 않고, access token 만료 시 폐기(다시 Resource Owner에게 허가 받아야 함)
      
        offline – access_type을 offline으로 지정하면 access token을 줄 때, refresh token을 같이 주는데 Client는 access token과 refresh token을 모두 다 저장하고 access token 만료 시 Resource Server에게 refresh token을 다시 보내서 access token을 재발급한다.

    -redirect_url : Resource Owner가 Resource Server의 인증화면으로 갔다가 Resource Owner가 허가하는 과정이 끝나면 Resource Server는 code값을 Client에 다시 보내주어야 하는데, 어느 주소로 Client에게 보내야 받을 수 있는지를 나타내는 것

8) access token 받기(Postman 사용 예시)

*Resource Server로부터 authorization code를 받은 것이 완료된 상황이라고 가정.

<img width="746" alt="testexample" src="https://user-images.githubusercontent.com/37721713/47208240-3e7b7b80-d3c8-11e8-9a34-5df3badb8200.PNG">



# 2. OAuth 테스트 예시


*Access Token 획득 테스트 예시(Postman 사용)
<img width="769" alt="oauthserver_test" src="https://user-images.githubusercontent.com/37721713/46660649-72011d80-cbf2-11e8-9c4e-9099531b300d.PNG">


*Access Token을 가지고 Api서버에 요청 테스트(Postman 사용)
<img width="763" alt="apiserver_test" src="https://user-images.githubusercontent.com/37721713/46660647-71688700-cbf2-11e8-932e-41e4cc5f1495.PNG">



# 3. OauthserverApplication.java
<img width="550" alt="oauthserverapplication" src="https://user-images.githubusercontent.com/37721713/46654432-c81a9480-cbe3-11e8-9f8f-44f36df075a2.PNG">

*@SpringBootApplication 

    -스프링부트 애플리케이션임을 나타내는 어노테이션을 의미


    -@Configuration, @EnableAutoConfiguration, @ComponentScan을 포함하는 의미를 갖는다. 


        @Configuration - 스프링의 자바 기반 구성 클래스를 지정한다. (스프링 컨테이너가 해당 클래스를 빈 정의의 소스로 사용한다는 의미)
                                             
                                             
        @EnableAutoConfiguration - classpath를 기반으로 ApplicationContext가 생성될 수 있도록 하는 역할.
                            
                            
        @ComponentScan - 메인클래스가 위치한 root package를 기본 속성으로 사용하여 자동으로 다른 컴포넌트 클래스들을 검색하여 빈으로 등록
                                             
                                             

*SpringApplication.run

    -스프링부트 애플리케이션을 실행시키는 메소드

# 4. OauthServerConfiguration.java
<img width="550" alt="oauthserverconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654433-c8b32b00-cbe3-11e8-9f35-8f0a5df2b029.PNG">


<img width="550" alt="oauthserverconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654434-c8b32b00-cbe3-11e8-83cd-9a4d44852034.PNG">


*@Autowired

    - @Autowired는 의존관계자동설정 어노테이션을 의미한다. Spring 설정 파일을 보고 자동으로 속성의 set 메서드에 해당하는 역할을 수행한다.
    
    
*@Configuration

    - 스프링의 자바 기반 구성 클래스를 지정한다. (스프링 컨테이너가 해당 클래스를 빈 정의의 소스로 사용한다는 의미)
    
    
*@EnableAuthorizationServer

    - OAuth2의 인증서버를 구성하는 어노테이션, 해당 어노테이션 설정만으로도 인증서버의 역할을 수행한다.
      스프링 부트 애플리케이션이 기동할 때 configure() 메소드들이 실행되면서 initialize가 된다. 이후 Authorization Server역할을 수행함.
    
    
*AuthorizationServerConfigurerAdapter

    - AuthorizationServerConfigurerAdapter는 AuthorizationServerConfigurer 인터페이스를 구현한다.
      AuthorizationServerConfigurer는 스프링 시큐리티 OAuth가 초기화 단계에서 호출하는 콜백 인터페이스로 configure 메소드를
      오버라이드하여 서비스의 동작을 바꿀 수 있다.
        

*configure(AuthorizationServerEndpointsConfigurer endpoints)

    - OAuth 서버에서 발급하는 토큰을 관리하는 주체(TokenStore), 인증에 대한 설정을 하는 주체(AuthenticationManager)를 설정한다.
      OAuth는 인가(Authorization)을 담당하기 때문에 인증(Athentication)은 스프링 시큐리티에서 처리해야 하는데, 
      이것을 연결해 주는 것이 AuthenticationManager이다.


*configure(ClientDetailsServiceConfigurer clients)

    - Client에 대한 관리를 어떻게 할 것인지에 대한 설정이다. (Client 저장소 설정에 대한 개념)
      위의 소스에서는 ClientDetailsService를 사용해 Client를 관리하는데, 이것은 DB(관계, 비관계 모두 포함)를 사용해 Client를 관리하고자 할 때
      해당 데이터 셋에 대해 고려하지 않고, HTTP 형태로 데이터를 주고 받고자 할 때 사용한다. 
      Bean으로 등록되어 있는 MyClientDetailService를 autowired하여 등록한다.
      Client 관리 방법에는 inMemory, DB 등의 방법이 존재한다.



# 5. TokenStoreConfiguration.java
<img width="550" alt="tokenstoreconfiguration" src="https://user-images.githubusercontent.com/37721713/46654435-c8b32b00-cbe3-11e8-8b16-045648ce9d81.PNG">


*설명

    - access token 저장을 위한 설정이다. JDBC를 통해 H2 DB에 접근하기 위해 DataSource를 사용하였다.
      토큰 관리는 일반적으로 inMemory, DB, JWT 3가지를 사용한다.
      토큰의 경우 매우 중요한 요소이기 때문에 반드시 발급하고 그것을 관리하는 과정이 필요하므로 DB, JWT 방식을 사용하는 것이 가장 좋다.
      대규모/대용량 아키텍처 기반의 애플리케이션은 토큰 암호화 과정에서 발생하는 워크로드도 부담이 될 수 있으므로 JWT 방식 사용을 선호한다.
    
    

# 6. MyUserDetailServiceConfiguration.java
<img width="550" alt="myuserdetailserviceconfiguration1" src="https://user-images.githubusercontent.com/37721713/46654430-c81a9480-cbe3-11e8-9ce6-8cf9f28d4668.PNG">


<img width="550" alt="myuserdetailserviceconfiguration2" src="https://user-images.githubusercontent.com/37721713/46654431-c81a9480-cbe3-11e8-982f-57d09d427e29.PNG">


*WebSecurityConfigurerAdapter

    - 웹 기반 보안 기능을 제공하는 클래스이며 URL에 대한 인증, 사용자 생성 등이 가능
      스프링 시큐리티를 사용하기 위해서 사용하는 가장 추천되는 방법이다. WebSecurityConfigurer 인터페이스에 대한 기본 메소드들이 구현되어 있고,
      이것을 오버라이딩 하여 개발자가 원하는 방식으로 동작할 수 있도록 수정할 수 있다.
    
    
*AuthenticationManager authenticationManagerBean()

    - OAuth는 인가를 담당하고, 인증은 스프링 시큐리티에서 처리하는데 AuthenticationManager가 OAuth와 스프링 시큐리티를 연결해주는 역할을 한다.
    
    
*configure(AuthenticationManagerBuilder authenticationManagerBuilder)

    - UserDetailsService(스프링에서 제공하는 사용자 세부 설정 서비스)를 설정하기 위한 오버라이딩이다. 
      즉, 인증 절차 지원을 위한 사용자 저장소 설정을 하는 의미를 갖는다.
      userDetailsService() 메소드를 통해 저장소에 대한 설정을 하고, 여기서는 UserDetailsService 인터페이스의 구현체를 사용한다.
      User에 대한 관리가 있어야 애플리케이션이 정상적으로 동작한다. 만약 이런 기능이 없으면 요청은 들어오나 User에 대한 인증을 못하므로
      로그인과 같은 서비스를 이용할 수가 없다.
    
    
*void configure(HttpSecurity http)

    - 인터셉터로 request를 안전하게 보호하여 보내기 위한 방법을 설정하기 위한 오버라이딩
      authorizedRequests()를 호출하고, 다음에 반환되는 객체로 호출되는 메소드들은 요청 보안 수준의 세부 설정을 한다.
      http.csrf().disable()은 CSRF(Cross-Site Request Forgery)에 대한 설정이다. Spring은 디폴트로 CSRF 방지 기능이 활성화되어 있다.
      CSRF 설정이 비활성화되면 제출되는 폼을 성공적으로 얻는데 문제가 발생할 수 있다. (테스트 때문에 여기서는 disable처리)
      스프링 시큐리티는 동기화 장치 토큰으로 CSRF보호에 대한 내용을 구현한다. 상태 변경 요청(PUT, POST...)을 가로채 CSRF 토큰을 확인하는데
      토큰이 일치하지 않거나 없으면 요청은 실패한다.


# 7. MyClientDetailService.java
<img width="550" alt="myclientdetailservice" src="https://user-images.githubusercontent.com/37721713/46654427-c781fe00-cbe3-11e8-81cb-b767a554f03e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


*ClientDetailsService 인터페이스

    - DB(관계/비관계형 모두 포함)를 사용하여 Client를 인증해야 한다고 할 때, Client의 데이터 구조에 신경쓰지 않고 데이터를 사용하기 위해
      Spring에서 제공되는 인터페이스
      
      
*loadClientByClientId(String clientId)

    - Client의 데이터 구조에 신경쓰지 않고, 단순히 HttpEntity 객체를 얻어와서 BaseClientDetails 객체를 만든다.
      ClientDetailsService 인터페이스 구현시 반드시 오버라이드 해야 하는 메소드이다.


# 8. MyUserDetailService.java
<img width="550" alt="myuserdetailservice" src="https://user-images.githubusercontent.com/37721713/46654429-c781fe00-cbe3-11e8-85c4-6b27d28d8d7e.PNG">


*@Component

    - 해당 클래스를 자동으로 빈에 등록하는 어노테이션


*RestTemplate

    - 스프링에서 제공하는 HTTP 통신에 유용하게 쓸 수 있는 템플릿이며, HTTP 서버와의 통신을 단순화하고 RESTful 원칙을 지킨다.


*UserDetailsService

    - User의 데이터 구조를 신경쓰지 않고, 단순히 HttpEntity 객체를 얻어와 UserDetails 객체를 만들기 위해 사용한다.
      (User의 데이터가 어떻게 유지되고 있는지 알지 못해도 된다.)
      
      
*loadUserByUsername(String username)

    - UserDetailService 인터페이스를 구현할 때 반드시 오버라이딩해야 하는 메소드이다.
      사용자명(username)을 기반으로 User를 찾기 위한 역할을 하며, UserDetails 객체를 반환한다.


# 9. Account.java
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


# 10. Client.java
<img width="550" alt="client" src="https://user-images.githubusercontent.com/37721713/46654426-c781fe00-cbe3-11e8-92f9-8c43e72d96d6.PNG">


*@Data

    - @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 모두 포함하는 어노테이션


*@NoArgsConstructor

    - 파라미터가 없는 기본 생성자를 만들어주는 어노테이션
    
    
*@AllArgsConstructor

    - 모든 필드 값을 파라미터로 받는 생성자를 만들어 주는 어노테이션


# 11. application.yml
<img width="219" alt="application" src="https://user-images.githubusercontent.com/37721713/46654425-c6e96780-cbe3-11e8-9b99-d014fde14ad7.PNG">  


*logging:level:org.springframework.security

    - org.springframework.security에만 로그 레벨을 디버그로 지정
