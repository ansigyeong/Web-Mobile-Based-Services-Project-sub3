package com.web.blog.dao.account;

import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.SignupRequest;


public interface AccountDao {
    public int insertAccount(SignupRequest user);
    public int updqteAccount(SignupRequest user);
    public AuthenticationRequest findByUsername(String username);
    public int findByAuthStatus(String username);
}