package com.web.blog.model.reply;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply  {
    private int rpNo;
    private String contents;
    private int rpLike;
    private Date createDate;
    private int queNo;
    private int userNo;
}