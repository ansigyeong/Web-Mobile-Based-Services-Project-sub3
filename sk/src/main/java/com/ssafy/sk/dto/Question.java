package com.ssafy.sk.dto;

public class Question {
	private int que_no, que_like, rp_cnt;
	private String lang, title, contents, create_date, writer;
	public Question() {}
	public Question(int que_no, int que_like, int rp_cnt, String lang, String title, String contents,
			String create_date, String writer) {
		super();
		this.que_no = que_no;
		this.que_like = que_like;
		this.rp_cnt = rp_cnt;
		this.lang = lang;
		this.title = title;
		this.contents = contents;
		this.create_date = create_date;
		this.writer = writer;
	}
	public int getQue_no() {
		return que_no;
	}
	public void setQue_no(int que_no) {
		this.que_no = que_no;
	}
	public int getQue_like() {
		return que_like;
	}
	public void setQue_like(int que_like) {
		this.que_like = que_like;
	}
	public int getRp_cnt() {
		return rp_cnt;
	}
	public void setRp_cnt(int rp_cnt) {
		this.rp_cnt = rp_cnt;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Override
	public String toString() {
		return "Question [que_no=" + que_no + ", que_like=" + que_like + ", rp_cnt=" + rp_cnt + ", lang=" + lang
				+ ", title=" + title + ", contents=" + contents + ", create_date=" + create_date + ", writer=" + writer
				+ "]";
	}
	
	
}
