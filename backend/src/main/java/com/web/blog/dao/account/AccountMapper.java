package com.web.blog.dao.account;

import java.util.List;

import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import com.web.blog.dto.account.SignupRequest;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    
    //회원가입
    @Insert("insert into user(name,email,pw,lang,authKey,createDate) values( #{user.name},#{user.email}, #{user.pw}, #{user.lang}, #{user.authKey} ,#{user.createDate})")
    public void insertAccount(@Param("user") SignupRequest user);

    //email 권한 수정
    @Update("update user set authStatus = 1 where authKey = #{user.authKey}")
    public int updateAuthStatus(@Param("user") SignupRequest user);    

    //로그인
    @Select("select * from user where email = #{email} ")
    public AuthenticationRequest findByUsername(String email);
    
    
    //관리자가 모든 사용자 조회
    @Select("SELECT * FROM USER where auth = #{account.auth}")
    public List readAllUsers(@Param("account") Account account);
   
    //status가 1일때만 로그인 가능
    @Select("select authStatus from user where email = #{username}")
    public int findByAuthStatus(String username);

    //회원 정보 받아오기
    @Select("select * from user where email = #{email}")
    public Account selectAccount(String email);
    
    //회원 수정
    @Update("update user set name=#{user.name}, pw=#{user.pw}, lang=#{user.lang} where email = #{user.email}" )
    public void updateAccount(@Param("user") Account user); 

    //회원 탈퇴
    @Delete("delete from user where email = #{email} ")
    public void deleteAccount(String email);

    // @Select("select authority from user_auth where ahth_id=#{id}")
    // public Account findRole(String id);
    // @Insert("insert into user_auth(auth_id,authority) values(#{auth_id}, #{authority})")
    // public int insertUserAuth(@Param("auth_id") String uid,@Param("authority") String role);

    
}