package com.web.blog.service.quiz;

import com.web.blog.dto.quiz.Quiz;

public interface QuizService {
    public Quiz search(int userNo);
    public void regist(int userNo, int grade);
}