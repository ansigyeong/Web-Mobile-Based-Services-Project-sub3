package com.web.blog.dao.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyMapper {
    
    //질문생성
    @Insert("insert into question(userNo,queNo,contents,createDate) values( #{reply.userNo}, #{reply.queNo},#{question.contents},#{reply.createDate})")
    public int writeReply(@Param("reply") Reply reply); 

    //질문 리스트
    @Select("select * from reply order by rpNo desc ")
    public List<Reply> replyList();


    
}