package com.web.blog.service.notice;

import java.util.List;

import com.web.blog.dao.notice.NoticeDao;
import com.web.blog.dto.notice.Notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeDao noticeDao;

    @Override
    public int writeNotice(Notice notice) {
        return noticeDao.writeNotice(notice);
    }

    @Override
    public int modifyNotice(Notice notice) {
        return noticeDao.modifyNotice(notice);
    }

    @Override
    public int deleteNotice(int noticeNo) {
        return noticeDao.deleteNotice(noticeNo);
    }

    @Override
    public Notice oneNotice(int noticeNo) {
        return noticeDao.oneNotice(noticeNo);
    }

    @Override
    public List<Notice> noticeList() {
        return noticeDao.noticeList();
    }

}