package com.web.blog.dao.reply;

import java.util.List;
import com.web.blog.model.reply.Reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDaoImpl implements ReplyDao {
   
    @Autowired
    ReplyMapper replyMapper;

	@Override
	public int writeReply(Reply reply) {
		return replyMapper.writeReply(reply);
	}

	@Override
	public List<Reply> replyList() {
		return replyMapper.replyList();
	}

}