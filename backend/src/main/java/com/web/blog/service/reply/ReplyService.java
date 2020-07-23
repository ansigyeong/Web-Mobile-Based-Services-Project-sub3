package com.web.blog.service.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;

public interface ReplyService {
    public int writeReply(Reply reply);
    public List<Reply> replyList();

}