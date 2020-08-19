package com.web.blog.dto.quiz;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz  {
    private int quizNo;
    private String createDate;
    private int grade;
    private int userNo;
}