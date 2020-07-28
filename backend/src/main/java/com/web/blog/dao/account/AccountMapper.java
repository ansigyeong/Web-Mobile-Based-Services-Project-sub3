package com.web.blog.dao.account;

import java.util.List;

import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import com.web.blog.model.account.SignupRequest;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    
    //회원가입
    @Insert("insert into user(userNo,name,email,pw,lang,auth_key,createDate) values( #{user.userNo}, #{user.name},#{user.email}, #{user.pw}, #{user.lang}, #{user.authKey} ,#{user.createDate})")
    public int insertAccount(@Param("user") SignupRequest user);

    //email 권한 수정
    @Update("update user set auth_status = 1 where auth_key = #{user.authKey}")
    public int updateAccount(@Param("user") SignupRequest user);    

    //로그인
    @Select("select * from user where email = #{email} ")
    public AuthenticationRequest findByUsername(String email);
    
    
    //관리자가 모든 사용자 조회
    @Select("SELECT * FROM USER where auth = #{account.auth}")
    public List readAllUsers(@Param("account") Account account);
   
    //status가 1일때만 로그인 가능
    @Select("select auth_status from user where email = #{username}")
    public int findByAuthStatus(String username);

    @Select("select * from user order by grade limit 3")
    public List<Account> hofList();

    @Select("select * from user where userNo=#{userNo}")
    public Account search(int userNo);

    // @Select("select authority from user_auth where ahth_id=#{id}")
    // public Account findRole(String id);
    // @Insert("insert into user_auth(auth_id,authority) values(#{auth_id}, #{authority})")
    // public int insertUserAuth(@Param("auth_id") String uid,@Param("authority") String role);

    
}