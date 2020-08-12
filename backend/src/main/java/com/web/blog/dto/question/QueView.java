package com.web.blog.dto.question;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueView  {
    private int queNo;
    private String lang;
    private String title;
    private String contents;
    private String createDate;
    private int userNo;
    private int rpCnt;
    private String name;

    private String tag1;
    private String tag2;
    private String tag3;
}