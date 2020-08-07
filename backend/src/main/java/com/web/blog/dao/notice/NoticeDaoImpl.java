package com.web.blog.dao.notice;

import java.util.List;

import com.web.blog.dto.notice.Notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDaoImpl implements NoticeDao {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public int writeNotice(Notice notice) {
        return noticeMapper.writeNotice(notice);
    }

    @Override
    public int modifyNotice(Notice notice) {
        return noticeMapper.modifyNotice(notice);
    }

    @Override
    public int deleteNotice(int noticeNo) {
        return noticeMapper.deleteNotice(noticeNo);
    }

    @Override
    public Notice oneNotice(int noticeNo) {
        return noticeMapper.oneNotice(noticeNo);
    }

    @Override
    public List<Notice> noticeList() {
        return noticeMapper.noticeList();
    }

}