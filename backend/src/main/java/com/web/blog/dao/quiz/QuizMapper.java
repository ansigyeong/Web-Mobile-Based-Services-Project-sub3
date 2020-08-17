package com.web.blog.dao.quiz;

import com.web.blog.dto.quiz.Quiz;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface QuizMapper {
    
    @Insert("insert into quiz(userNo, grade, createDate) values( #{userNo}, #{grade}, now() )")
    public void regist(int userNo, int grade); 

    @Select("select * from quiz where userNo = #{userNo} order by createDate desc limit 1")
    public Quiz search(int userNo);

}

