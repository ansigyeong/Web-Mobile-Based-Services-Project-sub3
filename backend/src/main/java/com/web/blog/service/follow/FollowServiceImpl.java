package com.web.blog.service.follow;

import java.util.List;

import com.web.blog.dao.cart.CartDao;
import com.web.blog.dao.follow.FollowDao;
import com.web.blog.dto.cart.Cart;
import com.web.blog.dto.follow.Follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;

    @Override
    public int regist(Follow follow) {
        return followDao.regist(follow);
    }

    @Override
    public int delete(int userNo, int followingNo) {
        return followDao.delete(userNo, followingNo);
    }

    @Override
    public List<Integer> followerList(int userNo) {
        return followDao.followerList(userNo);
    }

    @Override
    public List<Integer> followingList(int userNo) {
        return followDao.followingList(userNo);
    }


}