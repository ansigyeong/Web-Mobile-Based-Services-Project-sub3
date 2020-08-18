package com.web.blog.dao.quiz;

import com.web.blog.dto.quiz.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizDaoImpl implements QuizDao {

	@Autowired
	QuizMapper quizMapper;

	@Override
	public Quiz search(int userNo) {
		return quizMapper.search(userNo);
	}

	@Override
	public void regist(int userNo, int grade) {
		quizMapper.regist(userNo, grade);
	}

	

}