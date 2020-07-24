package com.web.blog.dao.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReplyMapper {
    
    @Insert("insert into reply(userNo,contents,queNo,createDate) values( #{reply.userNo}, #{reply.contents}, #{reply.queNo},#{reply.createDate})")
    public int writeReply(@Param("reply") Reply reply); 

    @Select("select * from reply where queNo=#{queNo} order by createDate desc ")
    public List<Reply> replyList(int queNo);

    @Delete("delete from reply where rpNo=#{rpNo}")
    public int deleteReply(int rpNo);

    @Select("select * from reply where rpNo = #{rpNo}")
    public Reply oneReply(int rpNo);

    @Update("update reply set contents=#{reply.contents} where rpNo=#{reply.rpNo}")
    public int modifyReply(@Param("reply") Reply reply);   


    
}