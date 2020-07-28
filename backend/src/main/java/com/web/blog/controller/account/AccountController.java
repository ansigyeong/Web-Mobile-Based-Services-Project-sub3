package com.web.blog.controller.account;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;
import com.web.blog.service.account.AccountService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.jsonwebtoken.Header;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    // @getma
    @GetMapping("/")
    @ApiOperation(value = "메인")
    public Object home() {
        // return new ResponseEntity<>(null, HttpStatus.OK);
        return "test";
    }

    @PostMapping("account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest user) {
        user.setCreateDate(new Date());

        accountService.insertAccount(user);
      
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        accountService.updateAuthStatus(user);
        System.out.println("이메일 인증 완료");
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    // @RequestParam String email, @RequestParam String pw, @RequestParam String userNo
    // @RequestBody AuthenticationRequest user
    @GetMapping("account/login")
    @ApiOperation(value="로그인")
    public Object login(@RequestParam String email, @RequestParam String pw){
       
       System.out.println("로그인 들어옴");
       AuthenticationRequest account;
       String jwt = "";
     
       account = accountService.findByUsername(email);
       BasicResponse result = new BasicResponse();
       if (account == null) {
           System.out.println("가입되지 않은 e-mail입니다.");
           result.data = "가입 되지 않은 e-mail";
           result.status = false;
           return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
       } else if (!passwordEncoder.matches(pw, account.getPw())) {
           System.out.println("잘못된 비밀번호입니다.");
           result.data = "잘못된 비밀번호입니다.";
           return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
       } else if(account != null){
            String gmail = account.getEmail();
            
                int cnt = accountService.findByAuthStatus(account.getEmail());// authStatus가 1이어야 로그인 가능(이메일 인증 시도 해야됨)
                if(cnt == 0){
                    System.out.println("e-mail인증 후 로그인 가능합니다.");
                    result.data = "e-mail인증 후 로그인 가능합니다.";
                    result.status = false;                           
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }else{
                    jwt = jwtToken.createToken(account.getEmail(), account.getRole());
                    
                    System.out.println("토큰 생성 : " + jwt);
                    result.data = "success";
                    result.status = true;
                    Map<String,String> map = new HashMap<>();
                    map.put("X-AUTH-TOKEN", jwt);
                    result.headers = map;
                    return new ResponseEntity<>(result,HttpStatus.OK);
                }
         

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
  
    }

    // @GetMapping("/account/logout")
    // @ApiOperation(value = "로그아웃")
    // public Object logout(Principal principal){
    //     principal = null;
    //     if(principal == null){
    //         return new ResponseEntity<>(principal, HttpStatus.OK);

    //     }

        
    // }
    @GetMapping("/users")
    @ApiOperation(value = "token확인")
    @ApiImplicitParams({ @ApiImplicitParam(name ="ACCESS-TOKEN", value="로그인 성공 후 access_token", required = true, paramType = "header")})
    public Object AuthPage(Principal principal){
       BasicResponse result = new BasicResponse();
    //    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       
        if(principal == null){ 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }else {
            //인증 성공 했으니 전달해주면됨.
            System.out.println("ㅇㅇ : "+ principal.getName());
            System.out.println(principal);
            return new ResponseEntity<>( principal , HttpStatus.OK);
        }
        
    }

    @GetMapping("/admin")
    @ApiOperation(value ="관리자")
    public Object adminPage(Map<String, Object> model) {
        return "/adminpage";
    }
    
    @GetMapping(value = "account/user")
    @ApiOperation(value = "회원 정보 조회")
    public Object selectAccount(Principal principal){
        
        if(principal == null ){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }else{
            BasicResponse result = new BasicResponse();
            Account account = accountService.selectAccount(principal.getName());
            result.data = account;
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK); 
        }
    } 


    @PutMapping(value="account/update")
    @ApiOperation(value = "회원 정보 수정")
    public Object updateAccount(@RequestBody Account user, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }else{
            accountService.updateAccount(user);
            Account account = accountService.selectAccount(user.getEmail());
            BasicResponse result = new BasicResponse();
            result.data = account;
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "account/delete")
    @ApiOperation(value = "회원 탈퇴")
    public Object deleteAccount(Principal principal){
        if(principal == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        }else{
            accountService.deleteAccount(principal.getName());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
    // @Value("${spring.url.base}")

    @GetMapping("/kakao")
    @ApiOperation(value = "kakao로그인")
    public Object login(@RequestParam("code") String code){
        System.out.println("code: " +code);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}