package com.ssafy.sk.repo;

import java.sql.SQLException;

import com.ssafy.sk.dto.User;

public interface UserDao {
	public User login(String uid, String pw) throws SQLException;
	public void signIn(User u) throws SQLException;
//	public User search(User u) throws SQLException;
//	public void userModify(User u) throws SQLException;
//	public void userDelete(String id) throws SQLException;
//	public String pwFind(String id, String name, String addr) throws SQLException;
}
