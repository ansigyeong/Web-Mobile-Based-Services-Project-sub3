package com.web.blog.dto.follow;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow  {
    private int followNo;
    private int userNo;
    private int followingNo;
}