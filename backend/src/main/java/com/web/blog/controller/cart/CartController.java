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
import com.web.blog.service.account.AccountService;
import com.web.blog.service.cart.CartService;
import com.web.blog.service.question.QuestionService;

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
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            List<Integer> cartList = cartService.cartList(userNo);
            for(int i = 0; i < cartList.size(); i++){
                Question q = questionService.oneQuestion(cartList.get(i));
                System.out.println(q);
                if(q.getLang().equals("c")){
                    cList.add(q);
                } else if(q.getLang().equals("cpp")){
                    cppList.add(q);
                } else if(q.getLang().equals("java")){
                    javaList.add(q);
                } else if(q.getLang().equals("python")){
                    pyList.add(q);
                } 
            }
            Map<String, Object> map = new HashMap<>();
            map.put("c", cList);
            map.put("cpp",cppList);
            map.put("java", javaList);
            map.put("python", pyList);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}