package com.ssafy.sk.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.sk.dto.User;
import com.ssafy.sk.repo.UserDao;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;

	public void signIn(User u) throws Exception {
		try {
			if (u == null) {
				throw new Exception("모든 정보를 입력해야 합니다.");
			}
			System.out.println("sv:"+u);

			dao.signIn(u);
		} catch (SQLException e) {
			throw new Exception("회원가입 중 오류 발생");
		}
	}

//	public void userModify(Member m) throws Exception {
//		try {
//			if (m.getId() == null || m.getPw() == null || m.getName() == null || m.getAddr() == null || m.getTel() == null) {
//				throw new Exception("모든 정보를 입력해야 합니다.");
//			}
//			dao.userModify(m);
//		} catch (SQLException e) {
//			throw new Exception("회원정보 변경 중 오류 발생");
//		}
//	}
//
//	public Member search(Member m) throws Exception {
//		try {
//			Member newm = dao.search(m);
//			if (newm == null) {
//				throw new Exception("모든 정보를 입력해야 합니다.");
//			}
//			return newm;
//		} catch (SQLException e) {
//			throw new Exception("회원정보 변경 중 오류 발생");
//		}
//	}

	public User login(String uid, String pw) throws Exception {
		if(uid == null || pw == null)
			return null;
		return dao.login(uid, pw);
	}

//	public void userDelete(String id) throws Exception {
//		dao.userDelete(id);
//	}
//
//	@Override
//	public String pwFind(String id, String name, String addr) throws Exception {
//		return dao.pwFind(id, name, addr);
//	}
}
