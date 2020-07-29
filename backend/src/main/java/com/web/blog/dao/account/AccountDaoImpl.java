package com.web.blog.dao.account;

import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountMapper accountMapper;

    @Override
    public void insertAccount(Account user) {
       accountMapper.insertAccount(user);
    }

    @Override
    public AuthenticationRequest findByUsername(String username){
        return accountMapper.findByUsername(username);
    }

    @Override
    public void updateAuthStatus(Account user) {
        accountMapper.updateAuthStatus(user);
    }

    @Override
    public int findByAuthStatus(String username) {
        return accountMapper.findByAuthStatus(username);
    }

    @Override
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

   

}