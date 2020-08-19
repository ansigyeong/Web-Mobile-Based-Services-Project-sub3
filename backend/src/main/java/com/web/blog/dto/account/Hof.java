package com.web.blog.dto.account;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hof  {
    private int userNo;
    private String name;
    private int grade;
    private int queCnt;
    private int rpCnt;
    private int rpLike;
    private int rank;
}