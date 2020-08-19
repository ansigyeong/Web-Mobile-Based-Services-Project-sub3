package com.web.blog.dao.notice;

import java.util.List;

import com.web.blog.dto.notice.Notice;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoticeMapper {
    
    @Insert("insert into notice(userNo,title,contents,createDate) values( #{notice.userNo}, #{notice.title},#{notice.contents},now())")
    public int writeNotice(@Param("notice") Notice notice);

    @Update("update notice set userNo=#{notice.userNo}, title=#{notice.title}, contents=#{notice.contents} where noticeNo=#{notice.noticeNo}")
    public int modifyNotice(@Param("notice") Notice notice);

    @Delete("delete from notice where noticeNo=#{noticeNo}")
    public int deleteNotice(int noticeNo);

    @Select("select * from notice where noticeNo = #{noticeNo}")
    public Notice oneNotice(int noticeNo);

    @Select("select * from notice order by createDate desc ")
    public List<Notice> noticeList();
}