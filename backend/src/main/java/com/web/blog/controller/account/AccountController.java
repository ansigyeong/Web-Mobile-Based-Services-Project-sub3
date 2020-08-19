package com.web.blog.controller.account;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.exceptions.PasswordExpiredException;
import com.web.blog.config.JwtTokenProvider;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.KakaoProfile;
import com.web.blog.dto.account.OAuthToken;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.account.TempKey;
import com.web.blog.service.follow.FollowService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.service.OAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.PutMapping;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), // 403error : 서버에 요청 전달되지만,
                                                                                         // 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

        @CrossOrigin(origins = { "http://localhost:3000" })
@RestController // spring4.1부터 추가. 기존controller + responseBody
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    JwtTokenProvider jwtToken;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    QuestionService questionService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FollowService followService;
    
    // @getma
    @GetMapping("/")
    @ApiOperation(value = "메인")
    public Object home() {
        // return new ResponseEntity<>(null, HttpStatus.OK);
        return "";
    }

    @PostMapping("account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@RequestBody Account user) {

        final BasicResponse result = new BasicResponse();
        accountService.insertAccount(user);
        result.data = "success";
        result.status = true;

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // @RequestParam String email, @RequestParam String pw, @RequestParam String
    // userNo
    // @RequestBody AuthenticationRequest user
    @GetMapping("account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam String email, @RequestParam String pw) {

        System.out.println("로그인 들어옴");
        AuthenticationRequest account;
        String jwt = "";

        account = accountService.findByUsername(email);

        BasicResponse result = new BasicResponse();
        Map<String, Object> map = new HashMap<>();

        if (account == null) {
            System.out.println("가입되지 않은 e-mail입니다.");
            map.put("msg", "가입되어 있지 않은 e-mail입니다.");
            result.data = map;
            result.status = false;
        } else if (!passwordEncoder.matches(pw, account.getPw())) {
            System.out.println("잘못된 비밀번호입니다.");
            map.put("msg", "비밀번호가 틀렸습니다.");
            result.data = map;
            result.status = false;
        } else if (account != null) {
            if (accountService.findByAuthStatus(account.getEmail()) == 0) {
                System.out.println("e-mail 인증 한 후 로그인 하여 주세요.");
                map.put("msg", "e-mail 인증 한 후 로그인 하여 주세요.");
                result.data = map;
                result.status = false;
            } else {
                jwt = jwtToken.createToken(account.getEmail(), account.getRole());
                System.out.println("토큰 생성 : " + jwt);
                map.put("ACCESS-TOKEN", jwt);
                result.data = map;
                result.status = true;

            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("account/eamilConfirm")
    @ApiOperation(value = "e-mail인증")
    public Object emailConfirm(@RequestParam String email, @RequestParam String authKey) {
        Account user = new Account();
        user.setEmail(email);
        user.setAuthKey(authKey);
        System.out.println("email: " + user.getEmail());
        System.out.println("email 인증키 : " + user.getAuthKey());
        user.setAuthStatus(1);
        accountService.updateAuthStatus(user);
        System.out.println("이메일 인증 완료");
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return "인증이 완료되었습니다. 이제 이 브라우저 탭을 닫고 hellow Code_Sea에서 로그인 할 수 있습니다.";

    }

    @GetMapping("/users")
    @ApiOperation(value = "token확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ACCESS-TOKEN", value = "로그인 성공 후 access_token", required = true, paramType = "header") })
    public Object AuthPage(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            System.out.println("ㅇㅇ : " + principal.getName());
            System.out.println(principal);
            return new ResponseEntity<>(principal, HttpStatus.OK);
        }

    }

    @GetMapping("/admin")
    @ApiOperation(value = "관리자")
    public Object adminPage(Map<String, Object> model) {
        return "/adminpage";
    }

    @GetMapping(value = "account/user")
    @ApiOperation(value = "회원 정보 조회")
    public Object selectAccount(Principal principal) {

        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            BasicResponse result = new BasicResponse();
            Account account = accountService.selectAccount(principal.getName());
            result.data = account;
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PutMapping(value = "account/update")
    @ApiOperation(value = "회원 정보 수정")
    public Object updateAccount(@RequestBody Account user, Principal principal) {
        System.out.println("role: "+ user.getRole());
        AuthenticationRequest account;
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else if(user.getRole().equals("ROLE_USER")) {
            System.out.println("11111");
            user.setEmail(principal.getName());
            account = accountService.findByUsername(principal.getName());
            if(!passwordEncoder.matches( user.getPw(),account.getPw())){
                System.out.println("2222");
                return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
            }else {
                BasicResponse result = new BasicResponse();
                System.out.println("3333");

                user.setNewPw(passwordEncoder.encode(user.getNewPw()));
                accountService.updateAccount(user);
                result.status = true;
                return new ResponseEntity<>(result, HttpStatus.OK);
    
            }
        } else {
            BasicResponse result = new BasicResponse();
            user.setEmail(principal.getName());
            System.err.println(user);
            accountService.updateKakao(user);
            result.status = true;              
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    } 

    @DeleteMapping(value = "account/delete")
    @ApiOperation(value = "회원 탈퇴")
    public Object deleteAccount(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } else {
            accountService.deleteAccount(principal.getName());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/auth/kakao/calback")
    @ApiOperation(value = "kakao로그인")
    public Object kakaoCallback(@RequestParam("code") String code) {
        System.out.println("code: " + code);
        // post방식으로 key=value 데이터를 code보내주면서 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "a02e0d55e3c3e558db7f944f5d746ac1");
        params.add("redirect_uri", "http://localhost/auth/kakao/calback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - posrt방식으로 보내고 response변수의 응답받음
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com//oauth/token", HttpMethod.POST,
                kakaoTokenRequest, String.class);

        // Gson, Json Simple,ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(" 카카오 엑세스 토큰 : "+ oauthToken.getAccess_token());

        // post방식으로 토큰 보내면서 사용자 정보 요청
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>( headers2);

        // Http 요청하기 - posrt방식으로 , response변수의 응답받음
        ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
        kakaoProfileRequest, String.class);
        System.out.println(response2.getBody());


        //User 객체에 담기
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Account user = new Account();
        user.setEmail(kakaoProfile.getKakao_account().getEmail());
        user.setName(kakaoProfile.getProperties().nickname);
        user.setPw(passwordEncoder.encode("danbi"));
        user.setRole("ROLE_KAKAO");
        String authKey = new TempKey().getKey(50, false); // 임의의 인증키 생성
        user.setAuthKey(authKey);
        String jwt = "";
        Account account = accountService.selectAccount(user.getEmail());
        
        BasicResponse result = new BasicResponse();
        Map<String,Object> map = new HashMap<>();


        if(account == null){
            System.out.println("기존 회원 아닙니다 자동 회원가입 진행");
            accountService.insertKakao(user);
        }
        
        jwt = jwtToken.createToken(user.getEmail(), user.getRole());
        System.out.println("토큰 생성 : " + jwt);
        map.put("ACCESS-TOKEN", jwt);
        result.data = map;
        result.status = true;
    
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/kakao")
    public Object kakaoLogin(@RequestParam String email, @RequestParam String name){
        Account user = new Account();
        user.setEmail(email);
        user.setName(name);
        user.setPw(passwordEncoder.encode("danbi"));
        user.setLang("c");
        user.setRole("ROLE_KAKAO");
        String authKey = new TempKey().getKey(50, false); // 임의의 인증키 생성
        user.setAuthKey(authKey);
        String jwt = "";
        Account account = accountService.selectAccount(user.getEmail());
        
        BasicResponse result = new BasicResponse();
        Map<String,Object> map = new HashMap<>();


        if(account == null){
            System.out.println("기존 회원 아닙니다 자동 회원가입 진행");
            accountService.insertKakao(user);
        }
        
        jwt = jwtToken.createToken(user.getEmail(), user.getRole());
        System.out.println("토큰 생성 : " + jwt);
        map.put("ACCESS-TOKEN", jwt);
        result.data = map;
        result.status = true;
    
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
}