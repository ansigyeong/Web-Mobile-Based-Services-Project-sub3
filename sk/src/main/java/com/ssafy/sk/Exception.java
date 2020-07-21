package com.ssafy.sk;

public class Exception extends RuntimeException{
	public Exception() {
		super("데이타를 처리 중 오류 발생");
	}
	public Exception(String msg) {
		super(msg);
	}
	
}
