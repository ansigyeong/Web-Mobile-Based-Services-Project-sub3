package com.web.blog.dao.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;


public interface ReplyDao {
    public int writeReply(Reply reply);
    public int modifyReply(Reply reply);
    public int deleteReply(int rpNo);
    public Reply oneReply(int rpNo);
    public List<Reply> replyList(int queNo);
    public Integer replyCount(int queNo);
    public List<Reply> myRp(int userNo);
    public Integer likeCnt(int userNo);
}