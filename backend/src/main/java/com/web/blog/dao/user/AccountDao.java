package com.web.blog.dao.user;

import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    @Autowired
    AccountMapper accountMapper;

    public int insertAccount(SignupRequest user){
        System.out.println("권한"+user.getAuth());
        // accountMapper.insertAccount(user);
        
        //accountMapper.insertUserAuth(account.getUid(), "ROLE_USER");
        return accountMapper.insertAccount(user);
    }

    public Account findByEmail(String username){
        return accountMapper.findByEmail(username);
    }


    
}