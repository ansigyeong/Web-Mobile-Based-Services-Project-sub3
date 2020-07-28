package com.web.blog.dao.account;


import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;


public interface AccountDao {
    public void insertAccount(Account user);
    public void updateAuthStatus(Account user);
    public AuthenticationRequest findByUsername(String username);
    public int findByAuthStatus(String username);
    public void deleteAccount(String email);
    public void updateAccount(Account user);
    public Account selectAccount(String email);
}