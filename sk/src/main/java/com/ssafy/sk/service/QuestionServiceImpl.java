package com.ssafy.sk.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sk.PageNavigation;
import com.ssafy.sk.dto.Question;
import com.ssafy.sk.repo.QuestionDao;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDao questionDao;

	public void writeQuestion(String writer, String title, String contents, String lang) throws Exception {
		questionDao.writeQuestion(writer, title, contents, lang);
	}
	
	public void deleteQuestion(int que_no) throws Exception {
		questionDao.deleteQuestion(que_no);
	}
	
	public void modifyQuestion(int que_no, String title, String contents, String lang) throws Exception {
		questionDao.modifyQuestion(que_no, title, contents, lang);
	}
	
	public List<Question> questionList() throws Exception {
		return questionDao.questionList();
	}
	
	public Question oneQuestion(int que_no) throws Exception {
		return questionDao.oneQuestion(que_no);
	}


}
