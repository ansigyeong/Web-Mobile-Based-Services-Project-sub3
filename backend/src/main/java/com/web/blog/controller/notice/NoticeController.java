package com.web.blog.controller.notice;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.notice.Notice;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.notice.NoticeService;

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
public class NoticeController {

    @Autowired
    NoticeService noticeService;
    @Autowired
    AccountService accountService;

    @PostMapping("notice")
    @ApiOperation(value ="공지작성")
    public Object write(@RequestBody Notice notice, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            account = accountService.search(userNo);
            String role = account.getRole();
            if(role.equals("ROLE_ADMIN")){
                notice.setUserNo(userNo);
                noticeService.writeNotice(notice);
                result.data = "write success";
            } else {
                result.data = "non admin";
            }
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("notice")
    @ApiOperation(value ="공지수정")
    public Object modify(@RequestBody Notice notice, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            account = accountService.search(userNo);
            String role = account.getRole();
            if(role.equals("ROLE_ADMIN")){
                notice.setUserNo(userNo);
                noticeService.modifyNotice(notice);
                result.data = "modify success";
            } else {
                result.data = "non admin";
            }
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }       
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
    @DeleteMapping("notice")
    @ApiOperation(value = "공지삭제")
    public Object delte(int noticeNo, Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            account = accountService.search(userNo);
            String role = account.getRole();
            if(role.equals("ROLE_ADMIN")){
                noticeService.deleteNotice(noticeNo);
                result.data = "delete success";
            } else {
                result.data = "non admin";
            }
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }  
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("notice")
    @ApiOperation(value = "공지목록")
    public Object list(Principal principal){
        final BasicResponse result = new BasicResponse();
        Map<String, Object> map = new HashMap<>();
        if(principal!=null){
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Account user = accountService.search(userNo);
            map.put("user", user);
        }
        try {
            List<Notice> list = noticeService.noticeList();
            for(int i=0; i < list.size(); i++){
                list.get(i).setCreateDate(list.get(i).getCreateDate().substring(0, 10));
            }
            map.put("list", list);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "fail";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("notice/detail")
    @ApiOperation(value = "상세공지")
    public Object detail(int noticeNo, Principal principal){
        final BasicResponse result = new BasicResponse();
        Map<String, Object> map = new HashMap<>();
        if(principal!=null){
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Account user = accountService.search(userNo);
            map.put("user", user);
        }
        try {
            Notice notice = noticeService.oneNotice(noticeNo);
            map.put("notice", notice);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }  
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}