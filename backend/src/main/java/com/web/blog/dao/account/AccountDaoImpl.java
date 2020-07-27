package com.web.blog.dao.account;

import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountMapper accountMapper;

    @Override
    public void insertAccount(SignupRequest user) {
       accountMapper.insertAccount(user);
    }

    @Override
    public AuthenticationRequest findByUsername(String username){
        return accountMapper.findByUsername(username);
    }

    @Override
    public void updqteAccount(SignupRequest user) {
        accountMapper.updateAccount(user);
    }

    @Override
    public int findByAuthStatus(String username) {
        return accountMapper.findByAuthStatus(username);
    }

    @Override
    public void deleteAccount(int userNo) {
       accountMapper.deleteAccount(userNo);
    }

}