package com.web.blog.dao.reply;

import java.util.List;

import com.web.blog.model.reply.Reply;


public interface ReplyDao {
    public int writeReply(Reply reply);
    // public int deleteQuestion();
    // public int modifyQuestion();
    public List<Reply> replyList();
    // public AuthenticationRequest findByUsername(String username);
    // public Question findByAuthStatus(String username);
}