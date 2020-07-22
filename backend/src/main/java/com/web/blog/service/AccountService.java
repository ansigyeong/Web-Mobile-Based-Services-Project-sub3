package com.web.blog.service;

import java.util.ArrayList;
import java.util.List;

import com.web.blog.dao.user.AccountDao;
import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AccountService implements UserDetailsService{
    @Autowired
    AccountDao accountDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountDao.findByEmail(username);
        try {
            System.out.println(account);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("User가 db에 없습니다.");
        }
        
        return new User(account.getEmail(), account.getPw(), account.getAuthorities());
    }

    //회원가입
    public int insertAccount(SignupRequest user){
        user.setPw(passwordEncoder.encode(user.getPw()));
        user.setAuth("ROLE_USER");

        System.out.println("비번: " +user.getPw());
        return accountDao.insertAccount(user);
    }

}