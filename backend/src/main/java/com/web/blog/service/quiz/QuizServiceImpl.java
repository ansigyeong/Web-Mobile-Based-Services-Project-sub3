package com.web.blog.service.quiz;

import com.web.blog.dao.quiz.QuizDao;
import com.web.blog.dto.quiz.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizDao quizDao;

    @Override
    public Quiz search(int userNo) {
        return quizDao.search(userNo);
    }

    @Override
    public void regist(int userNo, int grade) {
        quizDao.regist(userNo, grade);
    }




}