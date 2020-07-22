package com.web.blog.dao.account;

import com.web.blog.model.user.AuthenticationRequest;
import com.web.blog.model.user.SignupRequest;


public interface AccountDao {
    public int insertAccount(SignupRequest user);
    public int updqteAccount(SignupRequest user);
    public AuthenticationRequest findByUsername(String username);
    public int findByAuthStatus(String username);
}