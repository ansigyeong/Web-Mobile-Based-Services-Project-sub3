package com.web.blog.dao.quetag;

import java.util.List;

import com.web.blog.dto.tag.QueTag;
import com.web.blog.dto.tag.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QueTagDaoImpl implements QueTagDao {
    @Autowired
    QueTagMapper tagMapper;

    @Override
    public Tag searchTagName(String name) {
        return tagMapper.searchTagName(name);
    }

    @Override
    public Tag searchTagNo(int tagNo) {
        return tagMapper.searchTagNo(tagNo);
    }

    @Override
    public void registTag(String name) {
        tagMapper.registTag(name);
    }

    @Override
    public void deleteTag(int tagNo) {
        tagMapper.deleteTag(tagNo);
    }
    @Override
    public void registQueTag(int queNo, int tagNo) {
        tagMapper.registQueTag(queNo, tagNo);
    }

    @Override
    public void deleteQueTag(int queNo, int tagNo) {
        tagMapper.deleteQueTag(queNo, tagNo);
    }

    @Override
    public List<Tag> TagList() {
        return tagMapper.TagList();
    }

    @Override
    public List<QueTag> QueTagList(int queNo) {
        return tagMapper.QueTagList(queNo);
    }

    @Override
    public List<QueTag> tagNoList(int tagNo) {
        return tagMapper.tagNoList(tagNo);
    }


}