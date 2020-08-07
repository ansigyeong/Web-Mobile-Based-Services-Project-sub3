package com.web.blog.service.notice;

import java.util.List;

import com.web.blog.dto.notice.Notice;


public interface NoticeService {
    public int writeNotice(Notice notice);
    public int modifyNotice(Notice notice);
    public int deleteNotice(int noticeNo);
    public Notice oneNotice(int noticeNo);
    public List<Notice> noticeList();
}