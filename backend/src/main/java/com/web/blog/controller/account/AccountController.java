package com.web.blog.controller.account;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.user.AccountMapper;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.service.AccountService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
    AccountMapper accountMapper;

    // @getma
    @GetMapping("/")
    @ApiOperation(value ="메인")
    public Object home(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("account/login")
    @ApiOperation(value="로그인")
    public Object login(@RequestParam(required = true) final String email, @RequestParam(required = true) final String pw){
       ResponseEntity response = null;
       System.out.println("로그인 들어옴");

       
        return response;
    }

    @GetMapping("/admin")
    @ApiOperation(value ="관리자")
    public Object adminPage(Map<String, Object> model) {
        return "/adminpage";
    }

    @PostMapping("account/signup")
    @ApiOperation(value ="가입하기")
    public Object signup(@RequestBody Account account){
        System.out.println("가입하기 들어옴");
        ResponseEntity response = null;
      //  account.setAuth("ROLE_USER");
        Account vo = accountService.insertAccount(account);
        if(vo != null){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";  
        }else{
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}