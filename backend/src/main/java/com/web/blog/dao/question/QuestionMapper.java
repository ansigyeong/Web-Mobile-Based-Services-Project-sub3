package com.web.blog.dao.question;

import java.util.List;

import com.web.blog.model.question.Question;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
    
    //질문생성
    @Insert("insert into question(userNo,title,contents,lang,createDate) values( #{question.userNo}, #{question.title},#{question.contents}, #{question.lang},#{question.createDate})")
    public int writeQuestion(@Param("question") Question question); 

    //질문 리스트
    @Select("select * from question order by que_no desc ")
    public List<Question> questionList();


    
}