package com.web.blog.dao.account;

import java.util.List;
import com.web.blog.model.account.Account;
import com.web.blog.model.account.AuthenticationRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    

    @Insert("insert into user(name,email,pw,lang,authKey,createDate) values( #{user.name},#{user.email}, #{user.pw}, #{user.lang}, #{user.authKey} ,#{user.createDate})")
    public void insertAccount(@Param("user") Account user);

    //email 권한 수정
    @Update("update user set authStatus = 1 where authKey = #{user.authKey}")
    public int updateAuthStatus(@Param("user") Account user);    

    //로그인
    @Select("select * from user where email = #{email} ")
    public AuthenticationRequest findByUsername(String email);

    @Select("select * from user order by grade limit 3")
    public List<Account> hofList();

    @Select("select * from user where userNo=#{userNo}")
    public Account search(int userNo);


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

    
}