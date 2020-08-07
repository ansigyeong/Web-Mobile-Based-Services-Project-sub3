package com.web.blog.service.follow;

import java.util.List;

import com.web.blog.dto.follow.Follow;

public interface FollowService {
    public int regist(Follow follow);
    public int delete(int userNo, int followingNo);
    public List<Integer> followerList(int userNo);
    public List<Integer> followingList(int userNo);
}