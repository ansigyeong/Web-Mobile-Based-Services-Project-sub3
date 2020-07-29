package com.web.blog.service.cart;

import java.util.List;

import com.web.blog.dao.cart.CartDao;
import com.web.blog.dto.cart.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao cartDao;

    @Override
    public int registCart(Cart cart) {
        return cartDao.registCart(cart);
    }

    @Override
    public int deleteCart(int queNo) {
        return cartDao.deleteCart(queNo);
    }

    @Override
    public List<Integer> cartList(int userNo) {
        return cartDao.cartList(userNo);
    }


}