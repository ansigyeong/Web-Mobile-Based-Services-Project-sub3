package com.web.blog.dao.question;

import java.util.List;

import com.web.blog.dto.question.Question;


public interface QuestionDao {
    public int writeQuestion(Question question);
    public int modifyQuestion(Question question);
    public int deleteQuestion(int queNo);
    public Question oneQuestion(int queNo);
    public List<Question> questionList(String lang);
    public List<Question> myQue(int userNo);
    public List<Question> searchQue(String lang, String keyword);
    public List<Question> allquestionList();
    public List<Question> allsearchQue(String keyword);
}