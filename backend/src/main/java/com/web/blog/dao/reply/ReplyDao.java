package com.web.blog.dao.reply;

import java.util.List;

import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.rplike.Rplike;


public interface ReplyDao {
    public int writeReply(Reply reply);
    public int modifyReply(Reply reply);
    public int deleteReply(int rpNo);
    public Reply oneReply(int rpNo);
    public List<Reply> replyList(int queNo);
    public Integer replyCount(int queNo);
    public List<Reply> myRp(int userNo);
    public Integer likeCnt(int userNo);
    public int updatelike(int like, int rpNo);

    public Rplike check(int userNo, int rpNo);
    public int rplike(int userNo, int rpNo);
    public int delete(int userNo, int rpNo);
    public int rpLikeCnt(int rpNo);
}