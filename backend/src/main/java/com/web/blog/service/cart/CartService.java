package com.web.blog.service.cart;

import java.util.List;

import com.web.blog.dto.cart.Cart;

public interface CartService {
    public int registCart(Cart cart);
    public int deleteCart(int queNo);
    public List<Integer> cartList(int userNo);
}