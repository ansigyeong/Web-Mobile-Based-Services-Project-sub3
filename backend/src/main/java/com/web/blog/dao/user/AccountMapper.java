package com.web.blog.dao.user;

import java.util.List;

import com.web.blog.model.user.Account;
import com.web.blog.model.user.SignupRequest;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
    
    //회원가입
    @Insert("insert into user(uid,email,pw,auth) values( #{user.id}, #{user.email}, #{user.pw}, #{user.auth} )")
    public int insertAccount(@Param("user") SignupRequest user);

    //로그인
    @Select("select * from user where email = #{email} ")
    public Account findByEmail(String email);
    
    
    //관리자가 모든 사용자 조회
    @Select("SELECT * FROM USER where auth = #{account.auth}")
    public List readAllUsers(@Param("account") Account account);
    
    // @Select("select authority from user_auth where ahth_id=#{id}")
    // public Account findRole(String id);
    // @Insert("insert into user_auth(auth_id,authority) values(#{auth_id}, #{authority})")
    // public int insertUserAuth(@Param("auth_id") String uid,@Param("authority") String role);

    
}