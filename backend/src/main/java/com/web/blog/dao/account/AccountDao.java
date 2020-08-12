package com.web.blog.dao.account;

import java.util.List;

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
    public List<Account> hofList();
    public Account search(int userNo);
    public Account findByToken(String token);
    public void grade(int userNo, int grade);
    public void insertKakao(Account user);
}