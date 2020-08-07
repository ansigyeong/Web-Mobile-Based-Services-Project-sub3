package com.web.blog.controller.account;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.follow.FollowService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
            map.put("msg", "비밀번호가 틀렷습니다.");
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
    @ApiOperation(value ="e-mail인증")
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

        return new ResponseEntity<>(result, HttpStatus.OK);

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
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            accountService.updateAccount(user);
            Account account = accountService.selectAccount(user.getEmail());
            Map<String, Object> map = new HashMap<>();
            BasicResponse result = new BasicResponse();
            map.put("user", account);
            result.data = map;
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

    @GetMapping("/kakao")
    @ApiOperation(value = "kakao로그인")
    public Object login(@RequestParam("code") String code) {
        System.out.println("code: " + code);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}