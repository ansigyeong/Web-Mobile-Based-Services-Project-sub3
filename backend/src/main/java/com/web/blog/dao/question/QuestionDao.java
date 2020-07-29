package com.web.blog.dao.question;

import java.util.List;

import com.web.blog.dto.question.Question;


public interface QuestionDao {
    public int writeQuestion(Question question);
    public int modifyQuestion(Question question);
    public int deleteQuestion(int queNo);
    public Question oneQuestion(int queNo);
    public List<Question> questionList();
    public List<Question> detailList(String lang);
    public List<Question> myQue(int userNo);
}