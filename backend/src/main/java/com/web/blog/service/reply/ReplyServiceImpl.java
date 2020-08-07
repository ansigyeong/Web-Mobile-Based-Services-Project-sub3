package com.web.blog.service.reply;

import java.util.List;

import com.web.blog.dao.reply.ReplyDao;
import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.rplike.Rplike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyDao replyDao;

    @Override
    public int writeReply(Reply reply) {
        return replyDao.writeReply(reply);
    }

    @Override
    public int modifyReply(Reply reply) {
        return replyDao.modifyReply(reply);
    }

    @Override
    public int deleteReply(int rpNo) {
        return replyDao.deleteReply(rpNo);
    }

    @Override
    public Reply oneReply(int rpNo) {
        return replyDao.oneReply(rpNo);
    }

    @Override
    public List<Reply> replyList(int queNo) {
        return replyDao.replyList(queNo);
    }

    @Override
    public Integer replyCount(int queNo) {
        return replyDao.replyCount(queNo);
    }

    @Override
    public List<Reply> myRp(int userNo) {
        return replyDao.myRp(userNo);
    }

    @Override
    public Integer likeCnt(int userNo) {
        return replyDao.likeCnt(userNo);
    }

    @Override
    public int updatelike(int like, int rpNo) {
        return replyDao.updatelike(like, rpNo);
    }

    @Override
    public Rplike check(int userNo, int rpNo) {
        return replyDao.check(userNo, rpNo);
    }

    @Override
    public int rplike(int userNo, int rpNo) {
        return replyDao.rplike(userNo, rpNo);
    }

    @Override
    public int delete(int userNo, int rpNo) {
        return replyDao.delete(userNo, rpNo);
    }

    @Override
    public int rpLikeCnt(int rpNo) {
        return replyDao.rpLikeCnt(rpNo);
    }

}