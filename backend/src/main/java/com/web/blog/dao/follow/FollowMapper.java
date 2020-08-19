package com.web.blog.dao.follow;


import java.util.List;

import com.web.blog.dto.follow.Follow;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface FollowMapper {
    
    @Insert("insert into follow(userNo, followingNo) values( #{follow.userNo}, #{follow.followingNo})")
    public int regist(@Param("follow") Follow follow); 

    @Delete("delete from follow where userNo=#{userNo} and followingNo=#{followingNo}")
    public int delete(int userNo, int followingNo);

    @Select("select userNo from follow where followingNo=#{userNo}")
    public List<Integer> followerList(int userNo);

    @Select("select followingNo from follow where userNo=#{userNo}")
    public List<Integer> followingList(int userNo);
}