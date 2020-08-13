package com.web.blog.service.quetag;

import java.util.List;

import com.web.blog.dto.tag.QueTag;
import com.web.blog.dto.tag.Tag;

public interface QueTagService {
    public Tag searchTagName(String name);
    public Tag searchTagNo(int tagNo);
    public void registTag(String name);
    public void deleteTag(int tagNo);
    public void registQueTag(int queNo, int tagNo);
    public void deleteQueTag(int queNo, int tagNo);
    public List<Tag> TagList();
    public List<QueTag> QueTagList(int queNo);
    public List<QueTag> tagNoList(int tagNo);

}