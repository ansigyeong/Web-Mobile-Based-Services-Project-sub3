package com.web.blog.controller.account;

import java.util.ArrayList;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.Hof;
import com.web.blog.dto.follow.Follow;
import com.web.blog.dto.question.Question;
import com.web.blog.dto.reply.Reply;
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
        Map<String, Object> map = new HashMap<>();
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

    // 내 정보 // Account가져오고 follower랑 following 가져오고 질문 답변
    @GetMapping("account/profile")
    @ApiOperation(value = "프로필")
    public Object profile(Principal principal) {
        ResponseEntity response = null;
        System.out.println(principal);
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {

                Account user = accountService.search(userNo);
                List<Question> myque = questionService.myQue(userNo);
                List<Reply> myrp = replyService.myRp(userNo);
                List<Integer> list1 = followService.followerList(userNo);
                List<Integer> list2 = followService.followingList(userNo);
                ArrayList<Account> followerList = new ArrayList<>();
                ArrayList<Account> followingList = new ArrayList<>();
                for (int i = 0; i < list1.size(); i++) {
                    followerList.add(accountService.search(list1.get(i)));
                }
                for (int i = 0; i < list2.size(); i++) {
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
    }

    // 명전
    @GetMapping("account/hof")
    @ApiOperation(value = "명예의 전당")
    public Object hofList() {
        ResponseEntity response = null;
        try {
            ArrayList<Hof> data = new ArrayList<>();
            List<Account> list = accountService.hofList();
            for (int i = 0; i < list.size(); i++) {
                int userNo = list.get(i).getUserNo();
                String name = list.get(i).getName();
                int grade = list.get(i).getGrade();
                int queCnt = (questionService.myQue(userNo)).size();
                int rpCnt = (replyService.myRp(userNo)).size();
                int rpLike = 0;
                if (rpCnt != 0) {
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
    public Object myGrade(Principal principal) {
        System.out.println("prin " +principal);
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            ResponseEntity response = null;
            System.out.println("toekn 이름: "+ principal.getName());
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                Account user = accountService.search(userNo);
                String name = user.getName();
                int grade = user.getGrade();
                int queCnt = (questionService.myQue(userNo)).size();
                int rpCnt = (replyService.myRp(userNo)).size();
                int rpLike = 0;
                if (rpCnt != 0) {
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
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return response;
        }
    }

    // 팔로우
    @PostMapping("follow/regist")
    @ApiOperation(value = "팔로우하기")
    public Object regist(@RequestBody int followingNo, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            ResponseEntity response = null;
            try {
                Account account = accountService.findByToken(principal.getName());
                int userNo = account.getUserNo();
                Follow follow = new Follow();
                follow.setUserNo(userNo);
                follow.setFollowingNo(followingNo);
                followService.regist(follow);
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "follow success";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return response;
        }
    }

    @GetMapping("follow/delete")
    @ApiOperation(value = "팔로잉삭제")
    public Object delte(int followingNo, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            ResponseEntity response = null;
            try {
                followService.delete(userNo, followingNo);
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "delete success";
                response = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return response;

        }
    }

    @GetMapping("follow/follower")
    @ApiOperation(value = "내팔로워목록")
    public Object myFollower(Principal principal) {
        ResponseEntity response = null;
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                List<Integer> list = followService.followerList(userNo);
                ArrayList<Account> userList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    userList.add(accountService.search(list.get(i)));
                }
                final BasicResponse result = new BasicResponse();
                Map<String, Object> map = new HashMap<>();
                map.put("followerList", userList);
                result.data = map;
                result.status = true;
                response = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return response;
        }
    }

    @GetMapping("follow/following")
    @ApiOperation(value = "내팔로잉목록")
    public Object myFollowing(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            ResponseEntity response = null;
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                List<Integer> list = followService.followingList(userNo);
                ArrayList<Account> userList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    userList.add(accountService.search(list.get(i)));
                }
                final BasicResponse result = new BasicResponse();
                Map<String, Object> map = new HashMap<>();
                map.put("followingList", userList);
                result.data = map;
                result.status = true;
                response = new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return response;
        }
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
        BasicResponse result = new BasicResponse();

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