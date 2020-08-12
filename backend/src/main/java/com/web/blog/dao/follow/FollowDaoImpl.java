package com.web.blog.dao.follow;

import java.util.List;

import com.web.blog.dto.follow.Follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowDaoImpl implements FollowDao {

	@Autowired
	FollowMapper followMapper;

	@Override
	public int regist(Follow follow) {
		return followMapper.regist(follow);
	}

	@Override
	public int delete(int userNo, int followingNo) {
		return followMapper.delete(userNo, followingNo);
	}

	@Override
	public List<Integer> followerList(int userNo) {
		return followMapper.followerList(userNo);
	}

	@Override
	public List<Integer> followingList(int userNo) {
		return followMapper.followingList(userNo);
	}



}