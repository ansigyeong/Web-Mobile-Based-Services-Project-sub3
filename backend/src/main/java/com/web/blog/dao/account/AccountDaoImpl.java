package com.web.blog.dao.account;

import java.util.List;

import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountMapper accountMapper;

    public void insertAccount(Account user) {
       accountMapper.insertAccount(user);
    }

    @Override
    public AuthenticationRequest findByUsername(String username){
        return accountMapper.findByUsername(username);
    }

    public void updateAuthStatus(Account user) {
        accountMapper.updateAuthStatus(user);
    }

    @Override
    public int findByAuthStatus(String username) {
        return accountMapper.findByAuthStatus(username);
    }

    @Override
    public List<Account> hofList() {
        return accountMapper.hofList();
    }

    @Override
    public Account search(int userNo) {
        return accountMapper.search(userNo);
    }

    public void deleteAccount(String email) {
       accountMapper.deleteAccount(email);
    }

    @Override
    public void updateAccount(Account user) {
        accountMapper.updateAccount(user);
    }

    @Override
    public Account selectAccount(String email) {
        return accountMapper.selectAccount(email);
    }

    @Override
    public Account findByToken(String token) {
        return accountMapper.findByToken(token);
    }

    @Override
    public void grade(int userNo, int grade) {
        accountMapper.grade(userNo, grade);
    }

    @Override
    public void insertKakao(Account user) {
        accountMapper.insertKakao(user);
    }

    @Override
    public void updateKakao(Account user) {
        accountMapper.updateKakao(user);
    }

   

}