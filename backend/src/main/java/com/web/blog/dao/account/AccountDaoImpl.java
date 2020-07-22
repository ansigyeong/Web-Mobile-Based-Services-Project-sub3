package com.web.blog.dao.account;

import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    AccountMapper accountMapper;

    public int insertAccount(SignupRequest user){
        return accountMapper.insertAccount(user);
    }

    public Account findByEmail(String username){
        return accountMapper.findByEmail(username);
    }

    @Override
    public int updqteAccount(SignupRequest user) {
        return accountMapper.updateAccount(user);
    }


    
}