package com.web.blog.controller.tag;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.question.QueView;
import com.web.blog.dto.question.Question;
import com.web.blog.dto.question.Search;
import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.reply.Rp;
import com.web.blog.dto.rplike.Rplike;
import com.web.blog.dto.tag.QueTag;
import com.web.blog.dto.tag.Tag;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.quetag.QueTagService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), //403error : 서버에 요청 전달되지만, 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3d108.p.ssafy.io" })
@RestController //spring4.1부터 추가. 기존controller + responseBody 
public class TagController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ReplyService replyService;
    @Autowired
    AccountService accountService;
    @Autowired
    QueTagService quetagService;

    @PostMapping("tag")
    @ApiOperation(value ="태그등록")
    public Object tagRegist(@RequestBody Tag tag){ 
        final BasicResponse result = new BasicResponse();
        try{
            quetagService.registTag(tag.getName());
            result.status = true;
            result.data = "regist success";
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("tag")
    @ApiOperation(value = "태그삭제")
    public Object tagDelete(String name){
        final BasicResponse result = new BasicResponse();
        try {
            System.out.println(quetagService.searchTagName(name).getTagNo());
            quetagService.deleteTag(quetagService.searchTagName(name).getTagNo());
            result.data = "delete success";
        } catch (Exception e){
            System.out.println(e);
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("tag")
    @ApiOperation(value = "태그목록")
    public Object tagList(){
        final BasicResponse result = new BasicResponse();
        
        try {
            List<Tag> list = quetagService.TagList();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "fail";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("quetag")
    @ApiOperation(value ="질문태그등록")
    public Object quetagRegist(@RequestBody Question question, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null || question.getUserNo()!=accountService.findByToken(principal.getName()).getUserNo()){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try{
            String name = question.getName();
            if(quetagService.searchTagName(name)==null){
                quetagService.registTag(name);
            }
            quetagService.registQueTag(question.getQueNo(), quetagService.searchTagName(name).getTagNo());
            result.status = true;
            result.data = "regist success";
        } catch (Exception e) {
            System.out.println(e);
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("quetag")
    @ApiOperation(value = "질문태그삭제")
    public Object quetagDelete(int queNo, String name, Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null || questionService.oneQuestion(queNo).getUserNo()!=accountService.findByToken(principal.getName()).getUserNo()){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            quetagService.deleteQueTag(queNo, quetagService.searchTagName(name).getTagNo());
            result.data = "delete success";
        } catch (Exception e){
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("quetag")
    @ApiOperation(value = "질문태그목록")
    public Object quetagList(int queNo){
        final BasicResponse result = new BasicResponse();
        
        try {
            List<QueTag> quetaglist = quetagService.QueTagList(queNo);
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i < quetaglist.size(); i++){
                list.add(quetagService.searchTagNo(quetaglist.get(i).getTagNo()).getName());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "fail";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
}