package com.web.blog.dto.cart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart  {
    private int cartNo;
    private int userNo;
    private int queNo;
}