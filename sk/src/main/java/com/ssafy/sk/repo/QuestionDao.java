package com.ssafy.sk.repo;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.sk.dto.Question;

public interface QuestionDao {
	public void writeQuestion(String writer, String title, String contents, String lang) throws SQLException;
	public void deleteQuestion(int que_no) throws SQLException;
	public void modifyQuestion(int que_no, String title, String contents, String lnag) throws SQLException;
	public List<Question> questionList() throws SQLException;
	public Question oneQuestion(int que_no) throws SQLException;
}
