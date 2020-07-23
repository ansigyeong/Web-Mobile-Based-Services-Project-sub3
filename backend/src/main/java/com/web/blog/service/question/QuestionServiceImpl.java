package com.web.blog.service.question;

import java.util.List;

import com.web.blog.dao.question.QuestionDao;
import com.web.blog.model.question.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;

    @Override
    public int writeQuestion(Question question) {
        return questionDao.writeQuestion(question);
    }

    @Override
    public List<Question> questionList() {
        return questionDao.questionList();
    }



}