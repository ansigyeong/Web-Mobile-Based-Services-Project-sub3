package com.web.blog.dao.question;

import java.util.List;

import com.web.blog.dto.question.Question;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QuestionMapper {
    
    //질문생성
    @Insert("insert into question(userNo,title,contents,lang,createDate) values( #{question.userNo}, #{question.title},#{question.contents}, #{question.lang},now())")
    public int writeQuestion(@Param("question") Question question); 

    //질문 리스트
    @Select("select * from question where lang=#{lang} order by createDate desc ")
    public List<Question> questionList(String lang);

    @Delete("delete from question where queNo=#{queNo}")
    public int deleteQuestion(int queNo);

    @Select("select * from question where queNo = #{queNo}")
    public Question oneQuestion(int queNo);

    @Update("update question set title=#{question.title}, contents=#{question.contents}, lang=#{question.lang} where queNo=#{question.queNo}")
    public int modifyQuestion(@Param("question") Question question);    

    @Select("select * from question where userNo=#{userNo} order by createDate desc ")
    public List<Question> myQue(int userNo);

    @Select("select * from question where lang=#{lang} and (title like concat('%',#{keyword},'%') or contents like concat('%',#{keyword},'%')) order by createDate desc ")
    public List<Question> searchQue(String lang, String keyword);

    @Select("select * from question order by createDate desc ")
    public List<Question> allquestionList();

    @Select("select * from question where title like concat('%',#{keyword},'%') or contents like concat('%',#{keyword},'%') order by createDate desc ")
    public List<Question> allsearchQue(String keyword);
}