package com.web.blog.dao.question;

import java.util.List;


import com.web.blog.dto.question.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDaoImpl implements QuestionDao {
    @Autowired
    QuestionMapper questionMapper;


    @Override
    public int writeQuestion(Question question) {
        return questionMapper.writeQuestion(question);
    }

    @Override
    public List<Question> questionList(String lang) {
        return questionMapper.questionList(lang);
    }

    @Override
    public int deleteQuestion(int queNo) {
        return questionMapper.deleteQuestion(queNo);
    }

    @Override
    public Question oneQuestion(int queNo) {
        return questionMapper.oneQuestion(queNo);
    }

    @Override
    public int modifyQuestion(Question question) {
        return questionMapper.modifyQuestion(question);

    }

    @Override
    public List<Question> myQue(int userNo) {
        return questionMapper.myQue(userNo);
    }

    @Override
    public List<Question> searchQue(String lang, String keyword) {
        return questionMapper.searchQue(lang, keyword);
    }

    @Override
    public List<Question> allquestionList() {
        return questionMapper.allquestionList();
    }

    @Override
    public List<Question> allsearchQue(String keyword) {
        return questionMapper.allsearchQue(keyword);
    }


}