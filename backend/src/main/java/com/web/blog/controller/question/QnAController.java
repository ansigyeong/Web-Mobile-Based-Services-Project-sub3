package com.web.blog.controller.question;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.web.blog.config.JwtTokenProvider;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.account.Account;
import com.web.blog.model.cart.Cart;
import com.web.blog.model.question.Question;
import com.web.blog.model.reply.Reply;
import com.web.blog.service.cart.CartService;
import com.web.blog.service.question.QuestionService;
import com.web.blog.service.reply.ReplyService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ReplyService replyService;
    @Autowired
    JwtTokenProvider jwtToken;
    @Autowired
    CartService cartService;

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


    @PostMapping("question/modify")
    @ApiOperation(value ="질문수정")
    public Object modify(@RequestBody Question question){ 
        System.out.println("변경전 : "+ questionService.oneQuestion(question.getQueNo()));
        int que = questionService.modifyQuestion(question);
        ResponseEntity response = null;
        
        if(que == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println(question.getQueNo()+"번 질문 수정 완료");
            System.out.println("변경 후 : "+ questionService.oneQuestion(question.getQueNo()));
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            System.out.println("수정 실패");
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("question/question")
    @ApiOperation(value = "질문목록")
    public Object queList(){
        //
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) auth.getPrincipal();
        System.out.println("account : "+ account);

        //
        List<Question> list = questionService.questionList();
        // 댓글갯수가져오기
        ArrayList<Integer> listCnt = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listCnt.add(replyService.replyCount(list.get(i).getQueNo()));
        }
        ///////////
        ResponseEntity response = null;
        if(list.size() > 0){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println("list :"+list);
            System.out.println("cnt :"+listCnt);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("question/myQue")
    @ApiOperation(value = "내질문목록")
    public Object myQue(int userNo){
        ResponseEntity response = null;
        try {
            List<Question> list = questionService.myQue(userNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println("list :"+list);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }        
        return response;
    }

    @GetMapping("question/myRp")
    @ApiOperation(value = "내답변목록")
    public Object myRp(int userNo){
        ResponseEntity response = null;
        try {
            List<Reply> list = replyService.myRp(userNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println("list :"+list);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }        
        return response;
    }

    @GetMapping("question/deleteQuestion")
    @ApiOperation(value = "질문삭제")
    public Object queDelte(int queNo){
        ResponseEntity response = null;
        try {
            questionService.deleteQuestion(queNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            System.out.println(queNo+"번 질문 삭제 완료");
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("question/detailQuestion")
    @ApiOperation(value = "상세질문")
    public Object queDetail(int queNo){
        // 리플도 가져와야함
        Question question = questionService.oneQuestion(queNo);
        List<Reply> list = replyService.replyList(queNo);

        ResponseEntity response = null;
        if(question != null){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println(queNo+"번 상세질문 목록");
            System.out.println(question);
            System.out.println("답변 목록");
            System.out.println(list);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("reply/write")
    @ApiOperation(value ="답변하기")
    public Object writeReply(@RequestBody Reply reply){ 
        reply.setCreateDate(new Date());    
        int rp = replyService.writeReply(reply);
        ResponseEntity response = null;
        
        if(rp == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{

            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PostMapping("reply/modify")
    @ApiOperation(value ="질문수정")
    public Object modify(@RequestBody Reply reply){ 
        System.out.println("변경전 : "+ replyService.oneReply(reply.getRpNo()));
        int rp = replyService.modifyReply(reply);
        ResponseEntity response = null;
        
        if(rp == 1){
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println(reply.getRpNo()+"번 답변 수정 완료");
            System.out.println("변경 후 : "+ replyService.oneReply(reply.getRpNo()));
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            System.out.println("수정 실패");
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("reply/deleteReply")
    @ApiOperation(value = "답변삭제")
    public Object rpDelte(int rpNo){
        ResponseEntity response = null;
        try {
            replyService.deleteReply(rpNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            System.out.println(rpNo+"번 질문 삭제 완료");
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    
    @PostMapping("cart/regist")
    @ApiOperation(value ="찜등록")
    public Object regist(@RequestBody Cart cart){ 
           
        int ct = cartService.registCart(cart);
        // user_no을 가져온다.
        ResponseEntity response = null;
        
        if(ct == 1){
            System.out.println("찜등록완료");
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("cart/delete")
    @ApiOperation(value = "찜삭제")
    public Object delete(int queNo){
        ResponseEntity response = null;
        try {
            cartService.deleteCart(queNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            System.out.println(queNo+"번 질문 찜 삭제 완료");
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("cart/list")
    @ApiOperation(value = "찜목록")
    public Object List(int userNo){
        List<Integer> cartList = cartService.cartList(userNo);
        ResponseEntity response = null;
        System.out.println(cartList);
        ArrayList<Question> cList = new ArrayList<>();
        ArrayList<Question> cppList = new ArrayList<>();
        ArrayList<Question> javaList = new ArrayList<>();
        ArrayList<Question> pyList = new ArrayList<>();
        try {
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
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            System.out.println("c :"+cList+"c++ :"+cppList+"java :"+javaList+"python :"+pyList);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }
}