package com.web.blog.service.account;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.web.blog.dao.account.AccountDao;
import java.util.Date;
import com.web.blog.controller.exception.UserAlreadyExistException;
import com.web.blog.dao.account.AccountDao;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticationRequest user = accountDao.findByUsername(username);

        System.out.println("사용자 : " + user);

        return new User(user.getEmail(), user.getPw(), user.getAuthorities());
    }

    // 회원가입
    public void insertAccount(Account user) {

        user.setPw(passwordEncoder.encode(user.getPw())); // 비밀번호 암호화
        String authKey = new TempKey().getKey(50, false); // 임의의 인증키 생성
        user.setAuthKey(authKey);
        System.out.println("비번: " + user.getPw());
        System.out.println("임의의 eamil 인증키: " + user.getAuthKey());
        try {
            System.out.println("111");
            accountDao.insertAccount(user);
        } catch (Exception e) {
            throw new UserAlreadyExistException("회원가입 되어있는 email주소 입니다.");
        }
        MailHandler sendMail;
        // mail전송
        try {
            System.out.println("222");
            sendMail = new MailHandler(mailSender);
            sendMail.setSubject("[Hello Code_Sea 회원가입 이메일 인증]");
            sendMail.setText(
                    new StringBuffer().append("<h1>email 인증<h1>").append("<p>아래 링크를 클릭하시면 eamil 인증이 완료 됩니다.<p>")
                            .append("<a href='http://3.34.42.229:8080/account/eamilConfirm?").append("&email=")
                            .append(user.getEmail()).append("&authKey=").append(user.getAuthKey())
                            .append("' target='_blenk'>email 인증 확인</a>").toString());
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
    public void updateAuthStatus(Account user) {
        accountDao.updateAuthStatus(user);
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
    public List<Account> hofList() {
        return accountDao.hofList();
    }

    @Override
    public Account search(int userNo) {
        return accountDao.search(userNo);
    }

    public void deleteAccount(String email) {
        accountDao.deleteAccount(email);
    }

    @Override
    public void updateAccount(Account user) {
        accountDao.updateAccount(user);
    }

    @Override
    public Account selectAccount(String email) {
        return accountDao.selectAccount(email);
    }

    @Override
    public Account findByToken(String token) {
        return accountDao.findByToken(token);
    }

    @Override
    public void grade(int userNo, int grade) {
        accountDao.grade(userNo, grade);
    }

    @Override
    public void insertKakao(Account user) {
        accountDao.insertKakao(user);
    }

    @Override
    public void updateKakao(Account user) {
        accountDao.updateKakao(user);
    }

  

}