package com.web.blog.service.question;

import java.util.List;

import com.web.blog.dto.question.Question;

public interface QuestionService {
    public int writeQuestion(Question question);
    public List<Question> questionList();
    public int deleteQuestion(int queNo);
    public int modifyQuestion(Question question);
    public Question oneQuestion(int queNo);
    public List<Question> myQue(int userNo);
    public List<Question> detailList(String lang);
    public List<Question> searchQue(String lang, String search);

}