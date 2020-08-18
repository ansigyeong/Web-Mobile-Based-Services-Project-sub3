package com.web.blog.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rp  {
    private int rpNo;
    private String contents;
    private int rpLike;
    private String createDate;
    private int queNo;
    private String name;
    private String exist;
    private int userNo;
    private int grade;
    private String email;
}