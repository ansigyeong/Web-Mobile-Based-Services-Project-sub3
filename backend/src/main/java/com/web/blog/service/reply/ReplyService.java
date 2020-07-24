package com.web.blog.service.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;

public interface ReplyService {
    public int writeReply(Reply reply);
    public int modifyReply(Reply reply);
    public int deleteReply(int rpNo);
    public Reply oneReply(int rpNo);
    public List<Reply> replyList(int queNo);

}