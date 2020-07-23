package com.web.blog.controller.account;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import javax.validation.Valid;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.SignupRequest;
import com.web.blog.service.account.AccountService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), //403error : 서버에 요청 전달되지만, 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController //spring4.1부터 추가. 기존controller + responseBody 
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    JwtTokenProvider jwtToken;
    @Autowired
    PasswordEncoder passwordEncoder;

    // @getma
    @GetMapping("/")
    @ApiOperation(value ="메인")
    public Object home(){
        // return new ResponseEntity<>(null, HttpStatus.OK);
        return "test";
    }

    @PostMapping("account/signup")
    @ApiOperation(value ="가입하기")
    public Object signup(@Valid @RequestBody SignupRequest user){        
        user.setCreateDate(new Date());
        int account = accountService.insertAccount(user);
        ResponseEntity response = null;
        
        if(account == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{

            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("account/eamilConfirm")
    @ApiOperation(value = "이메일 인증하기")
    public Object emailConfirm(@RequestParam String email, @RequestParam String authKey){
        SignupRequest user = new SignupRequest();
        user.setEmail(email);
        user.setAuthKey(authKey);
        System.out.println("email: "+ user.getEmail());
        System.out.println("email 인증키 : "+ user.getAuthKey());
        user.setAuthStatus(1);
        int account = accountService.updateAccount(user);
        System.out.println("이메일 인증 완료");
        ResponseEntity response = null;
        if(account == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
        }else{
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;

    }

    // @RequestParam String email, @RequestParam String pw, @RequestParam String userNo
    @GetMapping("account/login")
    @ApiOperation(value="로그인")
    public Object login(@RequestBody AuthenticationRequest user){
       ResponseEntity response = null;
       System.out.println("로그인 들어옴");
       AuthenticationRequest account;
       String jwt = "";
     
       account = accountService.findByUsername(user.getEmail());
       if (account == null) {
           System.out.println("가입되지 않은 e-mail입니다.");
           response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       } else if (!passwordEncoder.matches(user.getPw(), account.getPw())) {
           System.out.println("잘못된 비밀번호입니다.");
           response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       } else {
           int cnt = accountService.findByAuthStatus(account.getEmail());// authStatus가 1이어야 로그인 가능(이메일 인증 시도 해야됨)
           if(cnt == 0){
               System.out.println("e-mail인증 후 로그인 가능합니다.");
               response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

           }else{
               jwt = jwtToken.createToken(account.getEmail(), account.getRole());
               System.out.println("토큰 생성 : " + jwt);
               response = new ResponseEntity<>(jwt, HttpStatus.OK);
           }
       }
  
        //로그인 성공 시 토큰 생성해서 같이 주기
       System.out.println("토큰 :  "+ jwt);
        return response;
    }

  


    @GetMapping("/account/logout")
    @ApiOperation(value ="로그아웃")
    public Object logout(@RequestParam int userNo){
        int cnt = accountService.deleteAccount(userNo);
        ResponseEntity response = null;

        if(cnt == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success"; 
            System.out.println("로그 아웃 성공");
        }else{
            System.out.println("로그아웃 실패 ");
        }

        return response;
        
    }

    @GetMapping("/admin")
    @ApiOperation(value ="관리자")
    public Object adminPage(Map<String, Object> model) {
        return "/adminpage";
    }

}