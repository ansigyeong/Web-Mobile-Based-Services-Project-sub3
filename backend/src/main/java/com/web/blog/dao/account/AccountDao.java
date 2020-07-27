package com.web.blog.dao.account;


import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;


public interface AccountDao {
    public void insertAccount(SignupRequest user);
    public void updqteAccount(SignupRequest user);
    public AuthenticationRequest findByUsername(String username);
    public int findByAuthStatus(String username);
    public void deleteAccount(int userNo);
}