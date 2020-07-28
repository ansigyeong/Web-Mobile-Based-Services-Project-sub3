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

    @Override
    public int deleteQuestion(int queNo) {
        return questionDao.deleteQuestion(queNo);
    }

    @Override
    public Question oneQuestion(int queNo) {
        return questionDao.oneQuestion(queNo);
    }

    @Override
    public int modifyQuestion(Question question) {
        return questionDao.modifyQuestion(question);
    }

    @Override
    public List<Question> myQue(int userNo) {
        return questionDao.myQue(userNo);
    }

    @Override
    public List<Question> detailList(String lang) {
        return questionDao.detailList(lang);
    }



}