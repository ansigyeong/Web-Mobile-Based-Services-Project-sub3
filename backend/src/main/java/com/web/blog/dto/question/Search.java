package com.web.blog.dto.question;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search  {
    private int type;
    private String lang;
    private String keyword;
}