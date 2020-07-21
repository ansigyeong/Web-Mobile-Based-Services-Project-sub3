package com.ssafy.sk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static final String URL = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
	static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String ID = "root";
	static final String PASSWORD = "7233";

	static {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, ID, PASSWORD);
	}

//	public static Connection getConnection() throws SQLException {
//		DataSource dataSource = null;
//		try {
//			Context iCtx = new InitialContext();
//			Context ctx = (Context) iCtx.lookup("java:comp/env");
//			dataSource = (DataSource) ctx.lookup("jdbc/ssafy");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		return dataSource.getConnection();
//	}
	
	public static void close(AutoCloseable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}