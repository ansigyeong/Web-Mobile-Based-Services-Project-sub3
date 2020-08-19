package com.web.blog.controller.reply;
import java.security.Principal;
import java.util.Date;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.rplike.Rplike;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ReplyController {

    @Autowired
    ReplyService replyService;
    @Autowired
    AccountService accountService;
    
    @PostMapping("reply")
    @ApiOperation(value ="댓글등록")
    public Object writeReply(@RequestBody Reply reply, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            reply.setUserNo(userNo);
            replyService.writeReply(reply);
            accountService.grade(userNo, accountService.search(userNo).getGrade()+5);
            result.status = true;
            result.data = "writeReply success";
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("reply")
    @ApiOperation(value ="댓글수정")
    public Object modify(@RequestBody Reply reply, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Reply rp = replyService.oneReply(reply.getRpNo());
            result.status = true;
            if(rp.getUserNo() == userNo){
                replyService.modifyReply(reply);
                result.data = "modifyReply success";
            } else {
                result.data = "user fail";
            }
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("reply")
    @ApiOperation(value = "댓글삭제")
    public Object rpDelte(int rpNo, Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Reply rp = replyService.oneReply(rpNo);
            result.status = true;
            if(rp.getUserNo() == userNo){
                replyService.deleteReply(rpNo);
                accountService.grade(userNo, accountService.search(userNo).getGrade()-5);
                result.data = "delteReply success";
            } else {
                result.data = "user fail";
            }
        } catch (Exception e){
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("reply/like")
    @ApiOperation(value ="댓글 좋아요")
    public Object likeReply(@RequestBody Rplike rplike, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            int rpNo = rplike.getRpNo();
            Account ac =  accountService.search(replyService.oneReply(rpNo).getUserNo());
             
            Rplike ck = replyService.check(userNo, rpNo);
            if(ck != null) {
                replyService.delete(userNo, rpNo);
                accountService.grade(ac.getUserNo(), ac.getGrade()-10);
            } else {
                replyService.rplike(userNo, rpNo);
                accountService.grade(ac.getUserNo(), ac.getGrade()+10);
            }
            int like = replyService.rpLikeCnt(rpNo);
            replyService.updatelike(like, rpNo);
            result.status = true;
            result.data = "like success";
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}