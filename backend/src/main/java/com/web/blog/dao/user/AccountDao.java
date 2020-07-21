package com.web.blog.dao.user;

import com.web.blog.model.user.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    @Autowired
    AccountMapper accountMapper;

    public Account insertAccount(Account account){
        System.out.println("권한"+account.getAuth());
        accountMapper.insertAccount(account);
        
        //accountMapper.insertUserAuth(account.getUid(), "ROLE_USER");
        return account;
    }

    public Account findByEmail(String username){
        return accountMapper.findByEmail(username);
    }


    
}