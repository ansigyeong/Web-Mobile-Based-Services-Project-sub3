package com.web.blog.controller.question;
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
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ReplyService replyService;
    @Autowired
    AccountService accountService;
    @Autowired
    QueTagService quetagService;

    @PostMapping("question")
    @ApiOperation(value ="질문등록")
    public Object write(@RequestBody Question question, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            question.setUserNo(userNo);
            questionService.writeQuestion(question);
            accountService.grade(userNo, accountService.search(userNo).getGrade()+5);

            /////////////////////
            ArrayList<String> tag = new ArrayList<>();
            if(question.getFirstTag()!=null)    tag.add(question.getFirstTag());
            if(question.getSecondTag()!=null)   tag.add(question.getSecondTag());
            if(question.getThirdTag()!=null)    tag.add(question.getThirdTag());

            for(int i = 0; i < tag.size(); i++){
                int TagNo = 0;
                if(quetagService.searchTagName(tag.get(i))==null){
                    quetagService.registTag(tag.get(i));
                }
                TagNo = quetagService.searchTagName(tag.get(i)).getTagNo();
                quetagService.registQueTag(questionService.myQue(userNo).get(0).getQueNo(),TagNo);
            }
            /////////////////////
            result.status = true;
            result.data = "write success";
        } catch (Exception e) {
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("question")
    @ApiOperation(value ="질문수정")
    public Object modify(@RequestBody Question question, Principal principal){ 
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            result.status = true;
            Question q = questionService.oneQuestion(question.getQueNo());

            if(userNo == q.getUserNo()) {
                questionService.modifyQuestion(question);
                result.data = "modify success";
            } else {
                result.data = "user fail";
            }
        } catch (Exception e) {
            result.data = "error";
        }        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("question")
    @ApiOperation(value = "질문삭제")
    public Object queDelte(int queNo, Principal principal){
        final BasicResponse result = new BasicResponse();
        if(principal == null){
            result.data = "token!!";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Question q = questionService.oneQuestion(queNo);
            result.status = true;
            if(userNo == q.getUserNo()){
                questionService.deleteQuestion(queNo);
                accountService.grade(userNo, accountService.search(userNo).getGrade()-5);
                result.data = "deleteQuestion success";
            } else {
                result.data = "user fail";
            }
        } catch (Exception e){
            result.data = "error";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("question")
    @ApiOperation(value = "질문목록")
    public Object queList(Search search){
        final BasicResponse result = new BasicResponse();
        System.out.println(search);
        String lang = search.getLang();
        List<Question> list;
        if(lang.equals("all")){
            if(search.getKeyword()!=null){
                String keyword = search.getKeyword();
                list = questionService.allsearchQue(keyword);
            } else { 
                list = questionService.allquestionList();
            }    
        } else {
            if(lang.equals("others")) lang="etc";
            if(search.getKeyword()!=null){
                String keyword = search.getKeyword();
                list = questionService.searchQue(lang, keyword);
            } else { 
                list = questionService.questionList(lang);
            }
        }
        try {
            ArrayList<QueView> queList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                List<QueTag> qtlist = quetagService.QueTagList(list.get(i).getQueNo());
                String a=""; 
                String b=""; 
                String c="";
                for(int j = 0; j < qtlist.size(); j++){
                    if(j==0) a = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==1) b = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==2) c= quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                }
                QueView qv = new QueView(list.get(i).getQueNo(),list.get(i).getLang(),list.get(i).getTitle(),
                         list.get(i).getContents(),list.get(i).getCreateDate(), list.get(i).getUserNo()
                         , replyService.replyCount(list.get(i).getQueNo()), accountService.search(list.get(i).getUserNo()).getName(),a,b,c);
                queList.add(qv);
            }
            if(search.getType()==1){
                Collections.sort(queList, new Comparator<QueView>(){

                    @Override
                    public int compare(QueView o1, QueView o2) {
                        return o2.getRpCnt()-o1.getRpCnt();
                    }
                    
                });
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", queList);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "fail";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 
    @GetMapping("question/detail")
    @ApiOperation(value = "상세질문")
    public Object queDetail(int queNo, int type, Principal principal){
        if(principal == null){
            Question question = questionService.oneQuestion(queNo);
            List<Reply> list = replyService.replyList(queNo);
            ArrayList<Rp> rpList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                Rp rp = new Rp();
                Reply reply = list.get(i);
                Account ac = accountService.search(reply.getUserNo());
                rp = new Rp(reply.getRpNo(), reply.getContents(), reply.getRpLike(), reply.getCreateDate()
                    , reply.getQueNo(), ac.getName(),"비로그인", reply.getUserNo(), ac.getGrade(), ac.getEmail());
                rpList.add(rp);
            }
            if(type==1){
                Collections.sort(rpList, new Comparator<Rp>(){
                    @Override
                    public int compare(Rp o1, Rp o2) {
                        return o2.getRpLike()-o1.getRpLike();
                    }
                });
            }
            List<QueTag> qtlist = quetagService.QueTagList(question.getQueNo());
            String a=""; 
            String b=""; 
            String c="";
            for(int j = 0; j < qtlist.size(); j++){
                if(j==0) a = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                if(j==1) b = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                if(j==2) c= quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
            }
            question.setFirstTag(a);
            question.setSecondTag(b);
            question.setThirdTag(c);
            Account user = accountService.search(question.getUserNo());
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("rpList", rpList);
            map.put("question", question);
            result.data = map;
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            Account account = accountService.findByToken(principal.getName());
            int userNo = account.getUserNo();
            Question question = questionService.oneQuestion(queNo);
            List<Reply> list = replyService.replyList(queNo);
            ArrayList<Rp> rpList = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                Rp rp = new Rp();
                Reply reply = list.get(i);
                Rplike ck = replyService.check(userNo, reply.getRpNo());
                String exist = "좋아요";
                if(ck != null)   exist = "좋아요취소";  
                Account ac = accountService.search(reply.getUserNo());
                rp = new Rp(reply.getRpNo(), reply.getContents(), reply.getRpLike(), reply.getCreateDate()
                    , reply.getQueNo(), ac.getName(), exist, reply.getUserNo(), ac.getGrade(), ac.getEmail());
                rpList.add(rp);
            }
            if(type==1){
                Collections.sort(rpList, new Comparator<Rp>(){
                    @Override
                    public int compare(Rp o1, Rp o2) {
                        return o2.getRpLike()-o1.getRpLike();
                    }
                });
            }
            List<QueTag> qtlist = quetagService.QueTagList(question.getQueNo());
            String a=""; 
            String b=""; 
            String c="";
            for(int j = 0; j < qtlist.size(); j++){
                if(j==0) a = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                if(j==1) b = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                if(j==2) c= quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
            }
            question.setFirstTag(a);
            question.setSecondTag(b);
            question.setThirdTag(c);
            Account user = accountService.search(question.getUserNo());
            final BasicResponse result = new BasicResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("rpList", rpList);
            map.put("question", question);
            result.data = map;
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    @GetMapping("tagList")
    @ApiOperation(value = "태그질문리스트")
    public Object tagqueList(int type, String tag){
        final BasicResponse result = new BasicResponse();

        try {
            ArrayList<QueView> queList = new ArrayList<>();
            List<QueTag> quetaglist = quetagService.tagNoList(quetagService.searchTagName(tag).getTagNo());
            for(int i = 0; i < quetaglist.size(); i++){
                Question q = questionService.oneQuestion(quetaglist.get(i).getQueNo());
                List<QueTag> qtlist = quetagService.QueTagList(q.getQueNo());
                String a=""; 
                String b=""; 
                String c="";
                for(int j = 0; j < qtlist.size(); j++){
                    if(j==0) a = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==1) b = quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                    if(j==2) c= quetagService.searchTagNo(qtlist.get(j).getTagNo()).getName();
                }
                QueView qv = new QueView(q.getQueNo(),q.getLang(),q.getTitle(),
                         q.getContents(),q.getCreateDate(), q.getUserNo()
                         , replyService.replyCount(q.getQueNo()), accountService.search(q.getUserNo()).getName(),a,b,c);
                queList.add(qv);
            }
            
            if(type==1){
                Collections.sort(queList, new Comparator<QueView>(){

                    @Override
                    public int compare(QueView o1, QueView o2) {
                        return o2.getRpCnt()-o1.getRpCnt();
                    }
                    
                });
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", queList);
            result.data = map;
            result.status = true;
        } catch (Exception e) {
            result.data = "fail";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}