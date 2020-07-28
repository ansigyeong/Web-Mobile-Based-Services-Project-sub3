# README

**OAuth2**

![image-20200724142648840](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20200724142648840.png)

<OAuth 인증/인가 과정>
자원 서버 : 페이스북, 카카오
클라이언트: 헬로우바다씨서비스
인증서버: 별도의 서비스가 될수도 있고 페이스북, 카카오가 될수도 있음
1.자원 소유자는 클라이언트에 접근
2.클라이언트는 자원소유자의 정보에 접근하기 위한 토큰을 얻기 위해 인증 서버에 접근
3.인증 서버는 자원소유자가 정말 그 자원에 대한 소유자가 맞는지 검증
4.인증 서버는 클라이언트가 신뢰할 수 있는 클라이언트인지 검증
5.모든 검증이 끝나면, 인증 서버는 클라이언트에게 토큰을 발급
6.클라이언트는 토큰을 가지고 자원 서버에게 접근
7.자원서버는 토큰이 유효한지 인증 서버에 확인하고 유효하다면 자원을 제공

외부서비스의 인증 및 권한부여를 관리하는 범용적인 프로토콜

1.사용자가 우리서버에게 인증요청

2.우리서버는 Ahtorization Request를 통해 사용자에게 인증할 수당(카카오,페이스북 로그인 url)을 보냄

3.사용자는 해당 요청을 통해 인증을 진행하고 인증을 완료했다는 신호로  Authorization Grant를 url에 실어 우리서버에게 보냄

4.우리 서버는 해당 권한증서를 Authorization Server에 보냄

5.Authorization Server는 권한증서를 확인 후 사용자가 맞다면 우리 서버에게 Access Token

6.우리 서버는 해당 토큰을 디비에 저장하거나 사용자에게 넘김

7.사용자가 resource server에 자원이 필요하면, 우리서버는 토큰을 담아 리소스 서버에요청

8.리소스 서버는 토큰이 유효한지 확인후 자원을 보냄

9.만일 만료되었거나 위조되엇다면, 우리서버는 Authorization server에 refresh token을 보내 재발급

10.그후 다시 리소스 서버에 자원 요청

11. refresh token도 만료 되었을때 사용자는 새로운 인증(다시 로그인)해야됨
12. 

(b),(c) 과정 : Authorization Grant - 토큰을 발급 받기 위해 선행되어야 하는 중요한 과정

<Authorization Grant방법>

1.인가 코드 승인 (Authorization Code)

2.암시적 승인 (Implicit)

3.자원 소유자 패스워드 승인 (Resource Owner Password)

4.클라이언트 인증 정보 승인 (Client Credentials)

-> 인가 코드 승인 방신으로 구현할 것임

**인가 코드 방식**

전제조건 : 회원은 카카오,페이스북에 가입되어 있다. 헬로우코드씨서비스는 resource server와 통신하기 위해 cilent ID,cilent Secert을 발급 받음

1) 자원 소유자(회원)가 클라이언트에 접근하면, 클라이언트는 자원 소유자에 대해 아무런 정보도 가지고 있지 않은 상태. 이때 자원 소유자에게 '카카오,페이스북(resource server)를 통해 로그인하기 '와 같은 링크를 제공하여 자원 서버로 부터 정보를 가져올 준비를 함 \

2)회원이 카카오를 통해 로그인하기 링크를 누르면 해당 링크를 통해 클라이언트는 인증서버로 요청을 보내게 되고, 이 요청을 받은 인증 서버는 http redirect(302)응답을 보냄. 이 때 리다이렉트 링크는 인증 서버의 인증 페이지가 되고, 따라서 회원은 인증 서버의 인증 페이지를 보게 된다.

이때 클라이언트가 인증서버로 처음 보내는 요청

![image-20200724143450195](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20200724143450195.png)

- client_id : 사전에 인증 서버로부터 발급받은 cilent id

- redirect_uri : 인증서버에서의 인증이 모두 끝난 뒤 다시 돌아갈 클라이언트 페이지

- statue : 난수값, 추후 클라이언트-인증서버 간의 식별을 위한 값

- scope : client가 원하는 자원의 범위. 추후 토큰을 발급 받았을 때, 해당 정보에 대한 접근 권한만을 얻게 된다.

  3) 인증 서버의 인증페이지에서, 자원 소유자가 본인의 계정/패스워드를 통해 인증. 이때 클라이언트는 회원의 계정/패스워드 볼수 없음. 회원이 직접 본인의 정보로 인증하므로 안전함. 인증 서버는 회원의 인증이 정상적으로 끝나면 앞서 클라이언트로부터 받았던 redirect_uri로 자원 소유자를 리다이렉트 시킴. 이때, 리다이렉트 uri는 authorization code, state 값 포함

  4)토큰 받기 위한 준비는 끝남. 클라이언트는 앞서 받은 authroization code,state ,그리고 사전에 api통신을 위해 발급받은 client id, client secerct등의 값을 가지고 인증서버에 토큰 요청. 인증 서버는 요청 정보들의 유효성 검증 후 토큰 발급. 이때, 앞서 클라이언트가 요청한 값의 옵션에 따라 인증서버에서 Refresh Token을 발급해줄 수 있음. 이는 Access token의 유효기간이 끝났을 경우 앞 절차를 다시 수행할 필요 없이 토큰을 재발급 받기 위한 값

  5)클라이언트는 토큰을 가지고 자원 서버에 자원 소유자의 정보를 요청. 자원 서버는 해당 토큰을 인증서버에 보내서 유효성 및 스코프 검증 . 인증 서버에서는 토큰에 해당하는 스코프를 알려주며, 자원서버에서는 그 스코프에 해당하는 정보를 클라이언트에게 주게됨. 

  

  접근 토큰(Access token) : 인증 후에 사용자가 서비스 제공자가 아닌 소비자를 통해 보호된 자원에 접근하기 위한 키를 포함한 값
  
  ---
  
  
  
  **Gmail이용해 email로 인증**

1. 사용자 테이블에 auth_key, auth_statut 생성
2. 회원가입시 임의 auth_key생성, auth_key는 0으로 디비에 방영
3. 사용자가 입력한 이메일로 관리자 발신의 인증메일 전송(db에 반영한 auth_key 정보 포함
4. 링크 클릭시 , 파라미터로 받은 auth_key를 확인하여 auth_status업데이터
    회원가입만 했을 경우 로그인시 이메일 인증 여부 확인 해야함
    insert into user(name,email,pw,lang,auth_key) values('ssafy','ssafy@ssafy','ssafy','java','key');
5. 0.authSataus가 0이면 alert("email인증 후 로그인 하여 주세요")



---

**ResponseEntity**

ResponseEntity는 @ResponseBody어노테이션과 같은 의미로, ResponseEntity를 return type으로 지정하면 json(default) 또는 xml Format으로 결과를 내려준다.

@ControllerAdvice : 클래스 레벨을 부여하면 컨트롤러 빈을 돕는 빈으로 기능 할 수 있다. 예를 들면 모든 컨트롤러 제어 범위에서 특정 예외가 발생하면 공통적인 처리 로직 작성

@Order - controllerAdvice빈 간의 우선순위를 조정할 수 있다.  **Ordered.HIGHEST_PRECEDENCE** : 최우선 순위

@ExceptionHandler : 특정 예외가 발생 했을 때의 처리 로직 작성 가능



---

REST API 상태코드

200 OK : 클라이언트의 요청을 서버가 정상적으로 처리했다.

201 Created : 클라이언트의 요청을 서버가 정상적으로 처리했고 새로운 리소스가 생겼다.(Post, put)

204 No Content : 클라이언트의 요청은 정상적, 그러나 컨텐츠 제공을 하지 않는다 (delete)

400 Bad Request : 클라이언트의 요청이 유효하지 않아 더 이상 작업을 진행하지 않는 경우 - 이때 파라미터의 위치(path,query,body),사용자 입력값 에러 이유를 꼭 명시

401 Unauthorized : 클라이언트가 권한이 없어 작업 진행을 할 수 없는 경우 - 비인증 (인증이 안된 상태) : 인증실패

403 Forbidden : 클라이언트가 권한이 없기 때문에 작업을 진행 할 수 없는 경우 : 권한 없음

409 Conflict : 요청에 대한 충돌 발생. post요청 시 이미 존재하는 자원있을때 



별도의 예외 코드 추가

1002 - 중복된 회원





reverse proxy

---



 

