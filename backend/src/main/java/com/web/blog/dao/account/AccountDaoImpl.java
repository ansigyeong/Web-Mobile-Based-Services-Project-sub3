package com.web.blog.dao.account;

import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountMapper accountMapper;

    @Override
    public int insertAccount(SignupRequest user){
        return accountMapper.insertAccount(user);
    }

    @Override
    public AuthenticationRequest findByUsername(String username){
        return accountMapper.findByUsername(username);
    }

    @Override
    public int updqteAccount(SignupRequest user) {
        return accountMapper.updateAccount(user);
    }

    @Override
    public int findByAuthStatus(String username) {
        return accountMapper.findByAuthStatus(username);
    }

    @Override
    public int deleteAccount(int userNo) {
        return accountMapper.deleteAccount(userNo);
    }

}