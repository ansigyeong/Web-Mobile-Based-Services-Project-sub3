package com.ssafy.sk.dto;

public class User {
	private String uid, name, email, pw, img, lang;
	private int grade, follower, following, total_like;
	private boolean enable;
	public User() {}
	public User(String uid, String name, String email, String pw, String img, String lang, int grade, int follower,
			int following, int total_like, boolean enable) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.pw = pw;
		this.img = img;
		this.lang = lang;
		this.grade = grade;
		this.follower = follower;
		this.following = following;
		this.total_like = total_like;
		this.enable = enable;
	}
	public User(String uid, String name, String email, String pw, String img, String lang) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.pw = pw;
		this.img = img;
		this.lang = lang;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public int getTotal_like() {
		return total_like;
	}
	public void setTotal_like(int total_like) {
		this.total_like = total_like;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", email=" + email + ", pw=" + pw + ", img=" + img + ", lang="
				+ lang + ", grade=" + grade + ", follower=" + follower + ", following=" + following + ", total_like="
				+ total_like + ", enable=" + enable + "]";
	}
	
	

	
	
}
