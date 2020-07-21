package com.ssafy.sk.service;

import com.ssafy.sk.dto.User;

public interface UserService {
	public User login(String uid, String pw) throws Exception;
	public void signIn(User u) throws Exception;
	/*
	 * public User search(User u) throws Exception; public void userModify(User u)
	 * throws Exception; public void userDelete(String uid) throws Exception; public
	 * String pwFind(String uid, String name, String addr) throws Exception;
	 */
}
