package com.web.blog.service.account;

import java.io.UnsupportedEncodingException;


import javax.mail.MessagingException;

import com.web.blog.controller.exception.UserAlreadyExistException;
import com.web.blog.dao.account.AccountDao;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountServiceImpl implements UserDetailsService, AccountService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AuthenticationRequest user = accountDao.findByUsername(username);
        
        System.out.println("사용자 : "+user);
      

        return new User(user.getEmail(), user.getPw(), user.getAuthorities());
    }

    // 회원가입
    public void insertAccount(SignupRequest user){

        user.setPw(passwordEncoder.encode(user.getPw())); // 비밀번호 암호화

        String authKey = new TempKey().getKey(50, false); // 임의의 인증키 생성
        user.setAuthKey(authKey);

        System.out.println("비번: " + user.getPw());
        System.out.println("임의의 eamil 인증키: " + user.getAuthKey());
        try {
        accountDao.insertAccount(user);
            
        } catch (Exception e) {
            throw new UserAlreadyExistException("회원가입 되어있는 email주소 입니다.");   
        }
        MailHandler sendMail;
        // mail전송
        try {
            sendMail = new MailHandler(mailSender);
            sendMail.setSubject("[바다이야기 회원가입 이메일 인증]");
            sendMail.setText(
                    new StringBuffer().append("<h1>email 인증<h1>").append("<p>아래 링크를 클릭하시면 eamil 인증이 완료 됩니다.<p>")
                            .append("<a href='http://localhost/account/eamilConfirm?")
                            .append("&email=").append(user.getEmail())
                            .append("&authKey=").append(user.getAuthKey()).append("' target='_blenk'>email 인증 확인</a>")
                            .toString());
            sendMail.setFrom("qoreksql456@gamil.com", "addmin");
            sendMail.setTo(user.getEmail());
            sendMail.send();
        } catch (MessagingException e) {
            System.out.println("mail 전송 fail");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.err.println("mail 한글깨짐");
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void updateAccount(SignupRequest user) {
        accountDao.updqteAccount(user);
    }

    @Override
    public int findByAuthStatus(String username) {
        return accountDao.findByAuthStatus(username);
    }

    @Override
    public AuthenticationRequest findByUsername(String username) {
        return accountDao.findByUsername(username);
    }

    @Override
    public void deleteAccount(int userNo) {
         accountDao.deleteAccount(userNo);
    }



}