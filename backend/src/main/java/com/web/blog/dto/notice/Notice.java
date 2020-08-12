package com.web.blog.dto.notice;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice  {
    private int noticeNo;
    private String title;
    private String contents;
    private String createDate;
    private int userNo;
}