package com.web.blog.controller.question;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.config.JwtTokenProvider;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.cart.Cart;
import com.web.blog.dto.question.QueView;
import com.web.blog.dto.question.Question;
import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.reply.Rp;
import com.web.blog.dto.rplike.Rplike;
import com.web.blog.service.account.AccountService;
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
    @Autowired
    AccountService accountService;

    @PostMapping("question/write")
    @ApiOperation(value ="질문하기")
    public Object write(@RequestBody Question question, Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            question.setCreateDate(new Date());   
            question.setUserNo(userNo);
            
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
    public Object modify(@RequestBody Question question, Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            final BasicResponse result = new BasicResponse();
            result.status = true;
            Question q = questionService.oneQuestion(question.getQueNo());

            if(userNo == q.getUserNo()) {
                questionService.modifyQuestion(question);
                result.data = "modify success";
            } else {
                result.data = "user fail";
                result.status = false;
            }
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
                         list.get(i).getContents(),list.get(i).getCreateDate(), list.get(i).getUserNo()
                         , replyService.replyCount(list.get(i).getQueNo()), accountService.search(list.get(i).getUserNo()).getName());
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

    @GetMapping("question/searchQue")
    @ApiOperation(value = "질문검색")
    public Object searchQue(String lang, String search){
        ResponseEntity response = null;
        try {
            List<Question> list = questionService.searchQue(lang, search);
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

    @GetMapping("question/myQue")
    @ApiOperation(value = "내질문목록")
    public Object myQue(Principal principal){
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
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
    public Object myRp(Principal principal){
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
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
    public Object queDelte(int queNo, Principal principal){
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Question q = questionService.oneQuestion(queNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            if(userNo == q.getUserNo()){
                questionService.deleteQuestion(queNo);
                result.data = "deleteQuestion success";
            } else {
                result.data = "user fail";
                result.status = false;
            }
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
            ArrayList<Rp> rpList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                Rp rp = new Rp();
                Reply reply = list.get(i);
                Account ac = accountService.search(reply.getUserNo());
                rp = new Rp(reply.getRpNo(), reply.getContents(), reply.getRpLike(), reply.getCreateDate(), reply.getQueNo(), ac.getName());
                rpList.add(rp);
            }
            Account user = accountService.search(question.getUserNo());
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("rpList", rpList);
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
    public Object writeReply(@RequestBody Reply reply, Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            reply.setUserNo(userNo);
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
    public Object modify(@RequestBody Reply reply, Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Reply rp = replyService.oneReply(reply.getRpNo());
            final BasicResponse result = new BasicResponse();
            result.status = true;
            if(rp.getUserNo() == userNo){
                replyService.modifyReply(reply);
                result.data = "modifyReply success";
            } else {
                result.data = "user fail";
                result.status = false;
            }
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("reply/deleteReply")
    @ApiOperation(value = "댓글삭제")
    public Object rpDelte(int rpNo, Principal principal){
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Reply rp = replyService.oneReply(rpNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            if(rp.getUserNo() == userNo){
                replyService.deleteReply(rpNo);
                result.data = "delteReply success";
            } else {
                result.data = "user fail";
                result.status = false;
            }
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>( null,HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("reply/like")
    @ApiOperation(value ="댓글 좋아요")
    public Object likeReply(@RequestBody Rplike rplike, Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            int rpNo = rplike.getRpNo();
            Rplike ck = replyService.check(userNo, rpNo);
            if(ck != null) {
                replyService.delete(userNo, rpNo);
            } else {
                replyService.rplike(userNo, rpNo);
            }
            int like = replyService.rpLikeCnt(rpNo);
            replyService.updatelike(like, rpNo);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "like success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
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
                        list.get(i).getContents(),list.get(i).getCreateDate(), list.get(i).getUserNo()
                        , replyService.replyCount(list.get(i).getQueNo()), accountService.search(list.get(i).getUserNo()).getName());
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
    public Object regist(@RequestBody Cart cart , Principal principal){ 
        ResponseEntity response = null;
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            cart.setUserNo(userNo);
            cart.setQueNo(cart.getQueNo());
            cartService.registCart(cart);
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "regist success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( null,HttpStatus.UNAUTHORIZED);
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
    public Object List(Principal principal){
        ResponseEntity response = null;
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