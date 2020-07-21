package com.ssafy.sk.repo;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.sk.dto.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public void signIn(User u) throws SQLException {
		System.out.println("rp:"+u);
		sqlSession.insert("User.signIn", u);
	}

//	public void userModify(User u) throws SQLException {
//		sqlSession.update("User.userModify", u);
//	}
//
//	public User search(User u) throws SQLException {
//		return sqlSession.selectOne("User.search", u);
//	}

	public User login(String uid, String pw) throws SQLException {
		User u = new User();
		u.setUid(uid);
		u.setPw(pw);
		System.out.println(u);
		return sqlSession.selectOne("User.login", u);
	}
//
//	public void userDelete(String id) throws SQLException {
//		sqlSession.delete("User.userDelete", id);
//	}

//	public String pwFind(String id, String name, String addr) throws SQLException {
//		User u = new User();
//		u.setId(id);
//		u.setName(name);
//		u.setAddr(addr);
//		return sqlSession.selectOne("User.pwFind", u);
//	}
}
