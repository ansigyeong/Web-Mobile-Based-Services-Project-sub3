package com.web.blog.dao.reply;
import java.util.List;
import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.rplike.Rplike;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReplyMapper {
    
    @Insert("insert into reply(userNo,contents,queNo,createDate) values( #{reply.userNo}, #{reply.contents}, #{reply.queNo},now())")
    public int writeReply(@Param("reply") Reply reply); 

    @Select("select * from reply where queNo=#{queNo} order by createDate desc ")
    public List<Reply> replyList(int queNo);

    @Delete("delete from reply where rpNo=#{rpNo}")
    public int deleteReply(int rpNo);

    @Select("select * from reply where rpNo = #{rpNo}")
    public Reply oneReply(int rpNo);

    @Update("update reply set contents=#{reply.contents} where rpNo=#{reply.rpNo}")
    public int modifyReply(@Param("reply") Reply reply);   

    @Select("select count(*) from reply where queNo=#{queNo} order by queNo desc")
    public Integer replyCount(int queNo);
    
    @Select("select * from reply where userNo=#{userNo} order by createDate desc ")
    public List<Reply> myRp(int userNo);

    @Select("select sum(rpLike) from reply where userNo=#{userNo}")
    public Integer likeCnt(int userNo);

    @Update("update reply set rpLike=#{like} where rpNo=#{rpNo}")
    public int updatelike(int like, int rpNo);

    
    @Select("select * from rplike where userNo=#{userNo} and rpNo=#{rpNo}")
    public Rplike check(int userNo, int rpNo);
    
    @Insert("insert into rplike(userNo,rpNo) values( #{userNo}, #{rpNo})")
    public int rplike(int userNo, int rpNo);

    @Delete("delete from rplike where userNo=#{userNo} and rpNo=#{rpNo}")
    public int delete(int userNo, int rpNo);

    @Select("select count(*) from rplike where rpNo=#{rpNo}")
    public int rpLikeCnt(int rpNo);
}