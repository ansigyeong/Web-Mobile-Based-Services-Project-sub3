package com.web.blog.controller.info;

import java.util.ArrayList;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.Hof;
import com.web.blog.dto.question.Question;
import com.web.blog.dto.reply.Reply;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.follow.FollowService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { 
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), // 403error : 서버에 요청 전달되지만,
                                                                                         // 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3d108.p.ssafy.io" })
@RestController // spring4.1부터 추가. 기존controller + responseBody
public class InfoController {

    @Autowired
    AccountService accountService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FollowService followService;

    
    @GetMapping("info/profile")
    @ApiOperation(value = "프로필")
    public Object profile(int userNo, Principal principal) {
        final BasicResponse result = new BasicResponse();
        int uN = 0;
        int modify = 0;
        Account account = new Account();
        if((userNo ==0 && principal!=null)||(principal!=null)&&(userNo==accountService.findByToken(principal.getName()).getUserNo())){
            modify = 1;
            account = accountService.findByToken(principal.getName());
            uN = account.getUserNo();
        } else {
            uN = userNo;
            account = accountService.search(uN);
        }
        userNo = uN;
            if(userNo==0){
                result.data = "token end";
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
        try {
            Hof hof = new Hof();
            Account user = accountService.search(userNo);
            // user.setCreateDate(user.getCreateDate().substring(0, 10));
            List<Question> myque = questionService.myQue(userNo);
            for(int i=0;i<myque.size();i++){
                myque.get(i).setCreateDate(myque.get(i).getCreateDate().substring(0, 10));
            }
            List<Reply> myrp = replyService.myRp(userNo);
            for(int i=0;i<myrp.size();i++){
                myrp.get(i).setCreateDate(myrp.get(i).getCreateDate().substring(0, 10));
            }
            List<Integer> list1 = followService.followerList(userNo);
            List<Integer> list2 = followService.followingList(userNo);
            List<Account> hofList = accountService.hofList();
            ArrayList<Account> followerList = new ArrayList<>();
            ArrayList<Account> followingList = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                followerList.add(accountService.search(list1.get(i)));
            }
            for (int i = 0; i < list2.size(); i++) {
                followingList.add(accountService.search(list2.get(i)));
            }
            for (int i = 0; i < hofList.size(); i++){
                if(hofList.get(i).getUserNo()==userNo){
                    int rpLike = 0;
                    if (myrp.size() != 0) {
                        rpLike = replyService.likeCnt(userNo);
                    }
                    hof = new Hof(userNo, hofList.get(i).getName(), hofList.get(i).getGrade(), myque.size(), myrp.size(), rpLike,i+1);
                    break;
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("modify", modify);
            map.put("hof",hof);
            map.put("user", user);
            map.put("myque", myque);
            map.put("myrp", myrp);
            map.put("followerList", followerList);
            map.put("followingList", followingList);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("info/hof")
    @ApiOperation(value = "명예의 전당")
    public Object hofList() {
        final BasicResponse result = new BasicResponse();
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
                data.add(new Hof(userNo, name, grade, queCnt, rpCnt, rpLike,i+1));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("info/mygrade")
    @ApiOperation(value = "내 등급")
    public Object myGrade(Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                List<Account> list = accountService.hofList();
                Hof hof = new Hof();
                Account account = accountService.findByToken(principal.getName());
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
                    if(userNo==account.getUserNo()) {
                        hof = new Hof(userNo, name, grade, queCnt, rpCnt, rpLike,i+1);
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("hof", hof);
                result.data = map;
                result.status = true;
            } catch (Exception e) {
                result.data = "error";
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("info/myQue")
    @ApiOperation(value = "내질문목록")
    public Object myQue(Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            List<Question> list = questionService.myQue(userNo);
            Map<String, Object> map = new HashMap<>();
            map.put("myqueList", list);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = new ResponseEntity<>(result, HttpStatus.OK);
        }        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
    @GetMapping("info/myRp")
    @ApiOperation(value = "내답변목록")
    public Object myRp(Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            List<Reply> list = replyService.myRp(userNo);
            Map<String, Object> map = new HashMap<>();
            map.put("myrpList", list);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}