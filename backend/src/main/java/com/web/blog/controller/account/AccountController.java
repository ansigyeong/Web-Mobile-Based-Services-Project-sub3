package com.web.blog.controller.account;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.Hof;
import com.web.blog.model.account.SignupRequest;
import com.web.blog.model.follow.Follow;
import com.web.blog.model.question.Question;
import com.web.blog.model.reply.Reply;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.follow.FollowService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @Autowired
    QuestionService questionService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FollowService followService;
    
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

    @GetMapping("/admin")
    @ApiOperation(value ="관리자")
    public Object adminPage(Map<String, Object> model) {
        return "/adminpage";
    }


    // 내 정보  // Account가져오고 follower랑 following 가져오고 질문 답변
    @GetMapping("account/profile")
    @ApiOperation(value = "프로필")
    public Object profile(int userNo){
        ResponseEntity response = null;
        try {
            Account user = accountService.search(userNo);
            List<Question> myque = questionService.myQue(userNo);
            List<Reply> myrp = replyService.myRp(userNo);
            List<Integer> list1 = followService.followerList(userNo);
            List<Integer> list2 = followService.followingList(userNo);
            ArrayList<Account> followerList = new ArrayList<>();
            ArrayList<Account> followingList = new ArrayList<>();
            for(int i = 0; i < list1.size(); i++){
                followerList.add(accountService.search(list1.get(i)));
            }
            for(int i = 0; i < list2.size(); i++){
                followingList.add(accountService.search(list2.get(i)));
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("myque", myque);
            map.put("myrp", myrp);
            map.put("followerList", followerList);
            map.put("followingList", followingList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("실패");
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    // 명전
    @GetMapping("account/hof")
    @ApiOperation(value = "명예의 전당")
    public Object hofList(){
        ResponseEntity response = null;
        try {
            ArrayList<Hof> data = new ArrayList<>(); 
            List<Account> list = accountService.hofList();
            for(int i = 0; i < list.size(); i++){
                int userNo = list.get(i).getUserNo();
                String name = list.get(i).getName();
                int grade = list.get(i).getGrade();
                int queCnt = (questionService.myQue(userNo)).size();
                int rpCnt = (replyService.myRp(userNo)).size();
                int rpLike = 0;
                if(rpCnt != 0){
                    rpLike = replyService.likeCnt(userNo);
                }
                data.add(new Hof(userNo, name, grade, queCnt, rpCnt, rpLike));
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("실패");
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("account/mygrade")
    @ApiOperation(value = "내 등급")
    public Object myGrade(int userNo){
        ResponseEntity response = null;
        try {
            Account user = accountService.search(userNo);
            String name = user.getName();
            int grade = user.getGrade();
            int queCnt = (questionService.myQue(userNo)).size();
            int rpCnt = (replyService.myRp(userNo)).size();
            int rpLike = 0;
            if(rpCnt != 0){
                rpLike = replyService.likeCnt(userNo);
            }
            Hof hof = new Hof(userNo, name, grade, queCnt, rpCnt, rpLike);
            
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("hof", hof);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("실패");
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    // 팔로우

    @PostMapping("follow/regist")
    @ApiOperation(value ="팔로우하기")
    public Object regist(@RequestBody Follow follow){ 
        ResponseEntity response = null;
        try {
            followService.regist(follow); 
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "follow success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("follow/delete")
    @ApiOperation(value = "팔로잉삭제")
    public Object delte(int userNo, int followingNo){
        ResponseEntity response = null;
        try {
            followService.delete(userNo, followingNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "delete success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("follow/follower")
    @ApiOperation(value = "내팔로워목록")
    public Object myFollower(int userNo){
        ResponseEntity response = null;
        try {
            List<Integer> list = followService.followerList(userNo);
            ArrayList<Account> userList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                userList.add(accountService.search(list.get(i)));
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("followerList", userList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }        
        return response;
    }

    @GetMapping("follow/following")
    @ApiOperation(value = "내팔로잉목록")
    public Object myFollowing(int userNo){
        ResponseEntity response = null;
        try {
            List<Integer> list = followService.followingList(userNo);
            ArrayList<Account> userList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                userList.add(accountService.search(list.get(i)));
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("followingList", userList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }        
        return response;
    }
}