package com.web.blog.dao.question;

import java.util.List;

import com.web.blog.model.question.Question;


public interface QuestionDao {
    public int writeQuestion(Question question);
    // public int deleteQuestion();
    // public int modifyQuestion();
    public List<Question> questionList();
    // public AuthenticationRequest findByUsername(String username);
    // public Question findByAuthStatus(String username);
}