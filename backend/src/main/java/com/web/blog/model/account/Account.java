package com.web.blog.model.account;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account  {
    private int userNo;
    private String name;
    private String email;
    private String pw;
    private String lang;
    private int grade;
    private int enable;
    private String role;
    private String authKey;
    private int authStatus;
    private Date createDate;


}