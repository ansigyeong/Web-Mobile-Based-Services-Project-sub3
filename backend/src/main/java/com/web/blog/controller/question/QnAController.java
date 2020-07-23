package com.web.blog.controller.question;
import java.util.Date;
import java.util.List;


import com.web.blog.config.JwtTokenProvider;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.question.Question;
import com.web.blog.service.question.QuestionService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), //403error : 서버에 요청 전달되지만, 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController //spring4.1부터 추가. 기존controller + responseBody 
public class QnAController {

    @Autowired
    QuestionService questionService;
    @Autowired
    JwtTokenProvider jwtToken;

    @PostMapping("question/write")
    @ApiOperation(value ="질문하기")
    public Object write(@RequestBody Question question){ 
        question.setCreateDate(new Date());    
        System.out.println("dd: "+question.getCreateDate());   
        int que = questionService.writeQuestion(question);
        // user_no을 가져온다.
        ResponseEntity response = null;
        
        if(que == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{

            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @GetMapping("question/question")
    @ApiOperation(value = "질문목록")
    public Object queList(){
        List<Question> list = questionService.questionList();

        ResponseEntity response = null;
        if(list.size() > 0){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println(list);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }
}