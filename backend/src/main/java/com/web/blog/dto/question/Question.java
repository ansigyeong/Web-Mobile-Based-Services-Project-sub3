package com.web.blog.dto.question;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question  {
    private int queNo;
    private String lang;
    private String title;
    private String contents;
    private String createDate;
    private int userNo;

    private String name;
    private String firstTag;
    private String secondTag;
    private String thirdTag;
    private int rpCnt;
}