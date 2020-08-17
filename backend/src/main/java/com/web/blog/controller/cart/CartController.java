package com.web.blog.controller.cart;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.cart.Cart;
import com.web.blog.dto.question.Question;
import com.web.blog.dto.tag.QueTag;
import com.web.blog.service.account.AccountService;
import com.web.blog.service.cart.CartService;
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
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class), //403error : 서버에 요청 전달되지만, 권한때문에 거절. 재인증하더라도 접속 거절
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController //spring4.1부터 추가. 기존controller + responseBody 
public class CartController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CartService cartService;
    @Autowired
    AccountService accountService;
    @Autowired
    QueTagService quetagService;
    @Autowired
    ReplyService replyService;

    @PostMapping("cart")
    @ApiOperation(value ="찜등록")
    public Object regist(@RequestBody Cart cart , Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            cart.setUserNo(userNo);
            cart.setQueNo(cart.getQueNo());
            cartService.registCart(cart);
            result.status = true;
            result.data = "regist success";
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("cart")
    @ApiOperation(value = "찜삭제")
    public Object delete(int queNo){
        final BasicResponse result = new BasicResponse();
        try {
            cartService.deleteCart(queNo);
            result.status = true;
            result.data = "delte success";
        } catch (Exception e){
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("cart")
    @ApiOperation(value = "찜목록")
    public Object List(Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        ArrayList<Question> cList = new ArrayList<>();
        ArrayList<Question> cppList = new ArrayList<>();
        ArrayList<Question> javaList = new ArrayList<>();
        ArrayList<Question> pyList = new ArrayList<>();
        ArrayList<Question> etcList = new ArrayList<>();
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Account user = accountService.search(userNo);
            List<Integer> cartList = cartService.cartList(userNo);
            for(int i = 0; i < cartList.size(); i++){
                Question q = questionService.oneQuestion(cartList.get(i));
                System.out.println(q);
                List<QueTag> qtlist = quetagService.QueTagList(q.getQueNo());
                String a=""; 
                String b=""; 
                String c="";
                for(int j = 0; j < qtlist.size(); j++){
                    if(j==0) a = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==1) b = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==2) c= quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                }
                q.setFirstTag(a);
                q.setSecondTag(b);
                q.setThirdTag(c);
                q.setRpCnt(replyService.replyCount(q.getQueNo()));
                if(q.getLang().equals("c")){
                    cList.add(q);
                } else if(q.getLang().equals("cpp")){
                    cppList.add(q);
                } else if(q.getLang().equals("java")){
                    javaList.add(q);
                } else if(q.getLang().equals("python")){
                    pyList.add(q);
                } else {
                    etcList.add(q);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("user",user);
            map.put("c", cList);
            map.put("cpp",cppList);
            map.put("java", javaList);
            map.put("python", pyList);
            map.put("etc", etcList);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}