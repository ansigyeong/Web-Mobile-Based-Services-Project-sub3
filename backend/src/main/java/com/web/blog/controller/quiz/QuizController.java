package com.web.blog.controller.quiz;

import java.util.Date;
import java.security.Principal;
import java.text.SimpleDateFormat;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.quiz.Quiz;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.quiz.QuizService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController // spring4.1부터 추가. 기존controller + responseBody
public class QuizController {

    @Autowired
    AccountService accountService;
    @Autowired
    QuizService quizService;

    
    @GetMapping("quiz")
    @ApiOperation(value = "퀴즈풀었는지확인")
    public Object check(Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal==null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try{
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Quiz qz = quizService.search(userNo);
            if(qz==null){
                result.data = 0;
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            String quizDate = qz.getCreateDate();
            Date now = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = transFormat.format(now);
            String qzDate = quizDate.substring(0, 4)+quizDate.substring(5, 7)+quizDate.substring(8, 10);
            String nwDate = time.substring(0, 4)+time.substring(5, 7)+time.substring(8, 10);
            if(!qzDate.equals(nwDate)){
                result.data = 0;
            } else {
                result.data = 1;
            }
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("quiz")
    @ApiOperation(value ="퀴즈풀기")
    public Object quiz(@RequestBody Quiz quiz, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        Account account = accountService.findByToken(principal.getName());
        int userNo = account.getUserNo();
        Quiz qz = quizService.search(userNo);
        if(qz!=null){
            String quizDate = qz.getCreateDate();
            Date now = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = transFormat.format(now);

            int dd = Integer.parseInt(quizDate.substring(8, 10));
            int tt = Integer.parseInt(quizDate.substring(11, 13))+9;
            if(tt/24==1)    dd++;
            String day = Integer.toString(dd);
            String qzDate = quizDate.substring(0, 4)+quizDate.substring(5, 7)+day;
            String nwDate = time.substring(0, 4)+time.substring(5, 7)+time.substring(8, 10);
            if(!qzDate.equals(nwDate)){
                quizService.regist(userNo, quiz.getGrade());
                accountService.grade(userNo, accountService.search(userNo).getGrade()+quiz.getGrade());
                result.data = "success";
            } else {
                result.data = "todayEnd";
            }
            result.status = true;
        } else {
            quizService.regist(userNo, quiz.getGrade());
            accountService.grade(userNo, accountService.search(userNo).getGrade()+quiz.getGrade());
            result.data = "success";
            result.status = true;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}