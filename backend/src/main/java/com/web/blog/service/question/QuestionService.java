package com.web.blog.service.question;

import java.util.List;

import com.web.blog.model.question.Question;

public interface QuestionService {
    public int writeQuestion(Question question);
    public List<Question> questionList();

}