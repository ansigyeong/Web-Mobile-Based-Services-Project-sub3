package com.web.blog.dao.question;

import java.util.List;


import com.web.blog.model.question.Question;

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
    public List<Question> questionList() {
        return questionMapper.questionList();
    }

}