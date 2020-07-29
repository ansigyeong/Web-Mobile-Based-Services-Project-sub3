package com.web.blog.dao.cart;


import java.util.List;

import com.web.blog.dto.cart.Cart;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CartMapper {
    
    @Insert("insert into cart(userNo, queNo) values( #{cart.userNo}, #{cart.queNo})")
    public int registCart(@Param("cart") Cart cart); 

    @Delete("delete from cart where queNo=#{queNo}")
    public int deleteCart(int queNo);

    @Select("select queNo from cart where userNo=#{userNo}")
    public List<Integer> cartList(int userNo);
}