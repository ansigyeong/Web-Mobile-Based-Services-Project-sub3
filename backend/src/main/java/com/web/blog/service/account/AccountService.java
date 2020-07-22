package com.web.blog.service.account;

import com.web.blog.model.user.SignupRequest;

import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {
    public UserDetails loadUserByUsername(String username);
    public int insertAccount(SignupRequest user);
    public int updateAccount(SignupRequest user);
}