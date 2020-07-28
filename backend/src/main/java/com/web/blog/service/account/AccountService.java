package com.web.blog.service.account;

import java.util.List;

import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.SignupRequest;

public interface AccountService {
    public int insertAccount(SignupRequest user);
    public int updateAccount(SignupRequest user);
    public int findByAuthStatus(String username);
    public AuthenticationRequest findByUsername(String username);
    public List<Account> hofList();
    public Account search(int userNo);

}