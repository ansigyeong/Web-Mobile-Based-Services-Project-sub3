package com.web.blog.dao.quetag;

import java.util.List;

import com.web.blog.dto.tag.QueTag;
import com.web.blog.dto.tag.Tag;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QueTagMapper {

    @Select("select * from tag where name = #{name}")
    public Tag searchTagName(String name);

    @Select("select * from tag where tagNo = #{tagNo}")
    public Tag searchTagNo(int tagNo);

    @Insert("insert into tag(name) values( #{name} )")
    public void registTag(String name);

    @Delete("delete from tag where tagNo=#{tagNo}")
    public void deleteTag(int tagNo);

    @Insert("insert into quetag(queNo,tagNo) values( #{queNo}, #{tagNo} )")
    public void registQueTag(int queNo, int tagNo);

    @Delete("delete from quetag where queNo=#{queNo} and tagNo=#{tagNo}")
    public void deleteQueTag(int queNo, int tagNo);

    @Select("select * from tag")
    public List<Tag> TagList();
    
    @Select("select * from quetag where queNo=#{queNo}")
    public List<QueTag> QueTagList(int queNo);
    
    @Select("select * from quetag where tagNo=#{tagNo}")
    public List<QueTag> tagNoList(int tagNo);
}