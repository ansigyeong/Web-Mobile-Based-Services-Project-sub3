package com.web.blog.service.reply;

import java.util.List;

import com.web.blog.dto.reply.Reply;

public interface ReplyService {
    public int writeReply(Reply reply);
    public int modifyReply(Reply reply);
    public int deleteReply(int rpNo);
    public Reply oneReply(int rpNo);
    public List<Reply> replyList(int queNo);
    public Integer replyCount(int queNo);
    public List<Reply> myRp(int userNo);
    public Integer likeCnt(int userNo);
}