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
    private Date createDate;
    private int userNo;
}