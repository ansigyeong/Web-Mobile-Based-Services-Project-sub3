package com.web.blog.dao.account;

import java.util.List;
import com.web.blog.dto.account.Account;
import com.web.blog.dto.account.AuthenticationRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    
    //회원가입
    @Insert("insert into user(name,email,pw,lang,authKey,createDate) values( #{user.name},#{user.email}, #{user.pw}, #{user.lang}, #{user.authKey} ,now())")
    public void insertAccount(@Param("user") Account user);

    //email 권한 수정
    @Update("update user set authStatus = 1 where authKey = #{user.authKey}")
    public int updateAuthStatus(@Param("user") Account user);    

    //로그인
    @Select("select * from user where email = #{email} ")
    public AuthenticationRequest findByUsername(String email);

    //명전
    @Select("select * from user order by grade desc")
    public List<Account> hofList();

    //모든 회원정보 조회
    @Select("select * from user where userNo=#{userNo}")
    public Account search(int userNo);
    
    //메일 인증
    @Select("select authStatus from user where email = #{username}")
    public int findByAuthStatus(String username);

    //회원 정보 받아오기
    @Select("select * from user where email = #{email}")
    public Account selectAccount(String email);
    
    //회원 수정(일반 계정)
    @Update("update user set name=#{user.name}, lang=#{user.lang}, pw= #{user.newPw} where email = #{user.email} " )
    public void updateAccount(@Param("user") Account user); 

    //회원 수정(카카오 계정)
    @Update("update user set name=#{user.name}, lang=#{user.lang} where email = #{user.email}")
    public void updateKakao(@Param("user") Account user);

    //회원 탈퇴
    @Delete("delete from user where email = #{email} ")
    public void deleteAccount(String email);
    
    //토큰안의 user계정 이용해서 찾기
    @Select("select userNo from user where email = #{token}")
    public Account findByToken(String token);

    //점수
    @Update("update user set grade = #{grade} where userNo = #{userNo}")
    public void grade(int userNo, int grade);

    //kakao계정 회원가입
    @Insert("insert into user(name,email,pw,role,authKey) values( #{user.name}, #{user.email}, #{user.pw}, #{user.role}, #{user.authKey}) ")
    public void insertKakao(@Param("user") Account user);
   
}