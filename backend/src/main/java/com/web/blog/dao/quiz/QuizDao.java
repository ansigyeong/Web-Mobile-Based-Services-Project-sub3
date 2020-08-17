package com.web.blog.dao.quiz;

import com.web.blog.dto.quiz.Quiz;

public interface QuizDao {
    public Quiz search(int userNo);
    public void regist(int userNo, int grade);
}