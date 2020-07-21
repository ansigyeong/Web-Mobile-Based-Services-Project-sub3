package com.ssafy.sk.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.sk.dto.Question;


@Repository
public class QuestionDaoImpl implements QuestionDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public void writeQuestion(String writer, String title, String contents, String lang) throws SQLException {
		Question n = new Question();
		n.setWriter(writer);
		n.setTitle(title);
		n.setContents(contents);
		n.setLang(lang);
		sqlSession.insert("Question.writeQuestion", n);
	}

	@Override
	public void deleteQuestion(int que_no) throws SQLException {
		sqlSession.delete("Question.deleteQuestion", que_no);
	}

	@Override
	public void modifyQuestion(int que_no, String title, String contents, String lang) throws SQLException {
		Question n = new Question();
		n.setQue_no(que_no);
		n.setTitle(title);
		n.setContents(contents);
		n.setLang(lang);
		sqlSession.update("Question.modifyQuestion", n);
	}

	@Override
	public List<Question> questionList() throws SQLException {
		return sqlSession.selectList("Question.questionList");
	}

	@Override
	public Question oneQuestion(int que_no) throws SQLException {
		return sqlSession.selectOne("Question.oneQuestion", que_no);
	}
	


}
