package com.ssafy.sk.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.sk.PageNavigation;
import com.ssafy.sk.dto.Question;

public interface QuestionService {
	public void writeQuestion(String writer, String title, String contents, String lang) throws Exception;
	public void deleteQuestion(int que_no) throws Exception;
	public void modifyQuestion(int que_no, String title, String contents, String lang) throws Exception;
	public List<Question> questionList() throws Exception;
	public Question oneQuestion(int que_no) throws Exception;
}
