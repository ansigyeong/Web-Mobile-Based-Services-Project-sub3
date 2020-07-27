package com.web.blog.service.account;

import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;

public interface AccountService {
    public void insertAccount(SignupRequest user);
    public void updateAccount(SignupRequest user);
    public int findByAuthStatus(String username);
    public AuthenticationRequest findByUsername(String username);
    public void deleteAccount(int userNo);
}