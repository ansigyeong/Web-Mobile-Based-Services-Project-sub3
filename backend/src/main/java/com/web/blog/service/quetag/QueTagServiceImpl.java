package com.web.blog.service.quetag;


import java.util.List;

import com.web.blog.dao.quetag.QueTagDao;
import com.web.blog.dto.tag.QueTag;
import com.web.blog.dto.tag.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QueTagServiceImpl implements QueTagService {
    @Autowired
    QueTagDao quetagDao;

    @Override
    public Tag searchTagName(String name) {
        return quetagDao.searchTagName(name);
    }

    @Override
    public Tag searchTagNo(int tagNo) {
        return quetagDao.searchTagNo(tagNo);
    }

    @Override
    public void registTag(String name) {
        quetagDao.registTag(name);
    }

    @Override
    public void deleteTag(int tagNo) {
        quetagDao.deleteTag(tagNo);
    }

    @Override
    public void registQueTag(int queNo, int tagNo) {
        quetagDao.registQueTag(queNo, tagNo);
    }

    @Override
    public void deleteQueTag(int queNo, int tagNo) {
        quetagDao.deleteQueTag(queNo, tagNo);
    }

    @Override
    public List<Tag> TagList() {
        return quetagDao.TagList();
    }

    @Override
    public List<QueTag> QueTagList(int queNo) {
        return quetagDao.QueTagList(queNo);
    }

    @Override
    public List<QueTag> tagNoList(int tagNo) {
        return quetagDao.tagNoList(tagNo);
    }


}