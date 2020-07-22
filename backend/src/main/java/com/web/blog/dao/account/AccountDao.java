package com.web.blog.dao.account;

import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;

public interface AccountDao {
    public int insertAccount(SignupRequest user);
    public int updqteAccount(SignupRequest user);
    public Account findByEmail(String username);
}