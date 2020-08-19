package com.web.blog.controller.follow;

import java.util.ArrayList;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.follow.Follow;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.follow.FollowService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), // 403error : 서버에 요청 전달되지만,
                                                                                         // 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://i3d108.p.ssafy.io" })
@RestController // spring4.1부터 추가. 기존controller + responseBody
public class FollowController {

    @Autowired
    AccountService accountService;
    @Autowired
    FollowService followService;

    
    @PostMapping("follow")
    @ApiOperation(value = "팔로우하기")
    public Object regist(@RequestBody Follow follow, Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                int followingNo = follow.getFollowingNo();
                Account account = accountService.findByToken(principal.getName());
                int userNo = account.getUserNo();
                follow.setUserNo(userNo);
                follow.setFollowingNo(followingNo);
                followService.regist(follow);
                accountService.grade(followingNo, accountService.search(followingNo).getGrade()+5);
                result.status = true;
                result.data = "follow success";
            } catch (Exception e) {
                result.data = "error";
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }


    @DeleteMapping("follow")
    @ApiOperation(value = "팔로우삭제")
    public Object delte(int followingNo, Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                followService.delete(userNo, followingNo);
                accountService.grade(followingNo, accountService.search(followingNo).getGrade()-5);
                result.status = true;
                result.data = "delete success";
            } catch (Exception e) {
                result.data = "error";
            }
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
    }

    @GetMapping("follow/follower")
    @ApiOperation(value = "내팔로워목록")
    public Object myFollower(Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                List<Integer> list = followService.followerList(userNo);
                ArrayList<Account> userList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    userList.add(accountService.search(list.get(i)));
                }
                Map<String, Object> map = new HashMap<>();
                map.put("followerList", userList);
                result.data = map;
                result.status = true;
            } catch (Exception e) {
                result.data = "error";
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("follow/following")
    @ApiOperation(value = "내팔로잉목록")
    public Object myFollowing(Principal principal) {
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            try {
                List<Integer> list = followService.followingList(userNo);
                ArrayList<Account> userList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    userList.add(accountService.search(list.get(i)));
                }
                Map<String, Object> map = new HashMap<>();
                map.put("followingList", userList);
                result.data = map;
                result.status = true;
            } catch (Exception e) {
                result.data = "error";
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

}