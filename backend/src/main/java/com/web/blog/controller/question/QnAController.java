package com.web.blog.controller.question;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.account.Account;
import com.web.blog.model.cart.Cart;
import com.web.blog.model.question.QueView;
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
        ResponseEntity response = null;
        try {
            question.setCreateDate(new Date());    
            questionService.writeQuestion(question);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "write success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PostMapping("question/modify")
    @ApiOperation(value ="질문수정")
    public Object modify(@RequestBody Question question){ 
        ResponseEntity response = null;
        try {
            questionService.modifyQuestion(question);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "modify success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }        
        return response;
    }

    @GetMapping("question/question")
    @ApiOperation(value = "질문목록")
    public Object queList(){
        ResponseEntity response = null;
        try {
            List<Question> list = questionService.questionList();
            ArrayList<QueView> queList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                QueView qv = new QueView(list.get(i).getQueNo(),list.get(i).getLang(),list.get(i).getTitle(),
                list.get(i).getContents(),list.get(i).getCreateDate(), list.get(i).getUserNo(), replyService.replyCount(list.get(i).getQueNo()));
                queList.add(qv);
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("list", queList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
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
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            result.data = map;
            result.status = true;
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
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            result.data = map;
            result.status = true;
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
            result.data = "deleteQuestion success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("question/detailQuestion")
    @ApiOperation(value = "상세질문")
    public Object queDetail(int queNo){
        ResponseEntity response = null;
        try {
            Question question = questionService.oneQuestion(queNo);
            List<Reply> list = replyService.replyList(queNo);
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("question", question);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("reply/write")
    @ApiOperation(value ="댓글달기")
    public Object writeReply(@RequestBody Reply reply){ 
        ResponseEntity response = null;
        try {
            reply.setCreateDate(new Date());    
            replyService.writeReply(reply);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "writeReply success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PostMapping("reply/modify")
    @ApiOperation(value ="댓글수정")
    public Object modify(@RequestBody Reply reply){ 
        ResponseEntity response = null;
        try {
            replyService.modifyReply(reply);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "modifyReply success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("reply/deleteReply")
    @ApiOperation(value = "댓글삭제")
    public Object rpDelte(int rpNo){
        ResponseEntity response = null;
        try {
            replyService.deleteReply(rpNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "delteReply success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("question/detailList")
    @ApiOperation(value = "상세질문목록")
    public Object detailList(String lang){
        
        ResponseEntity response = null;
        try {
            List<Question> list = questionService.detailList(lang);
            ArrayList<QueView> queList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                QueView qv = new QueView(list.get(i).getQueNo(),list.get(i).getLang(),list.get(i).getTitle(),
                list.get(i).getContents(),list.get(i).getCreateDate(), list.get(i).getUserNo(), replyService.replyCount(list.get(i).getQueNo()));
                queList.add(qv);
            }
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("list", queList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PostMapping("cart/regist")
    @ApiOperation(value ="찜등록")
    public Object regist(@RequestBody Cart cart){ 
        ResponseEntity response = null;
        try {
            cartService.registCart(cart);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "regist success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
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
            result.data = "delte success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("cart/list")
    @ApiOperation(value = "찜목록")
    public Object List(int userNo){
        ResponseEntity response = null;
        ArrayList<Question> cList = new ArrayList<>();
        ArrayList<Question> cppList = new ArrayList<>();
        ArrayList<Question> javaList = new ArrayList<>();
        ArrayList<Question> pyList = new ArrayList<>();
        try {
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
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("c", cList);
            map.put("cpp",cppList);
            map.put("java", javaList);
            map.put("python", pyList);
            result.data = map;
            result.status = true;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }
}