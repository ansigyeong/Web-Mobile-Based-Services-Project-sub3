package com.web.blog.dao.cart;

import java.util.List;

import com.web.blog.dto.cart.Cart;

public interface CartDao {
    public int registCart(Cart cart);
    public int deleteCart(int queNo);
    public List<Integer> cartList(int userNo);
}