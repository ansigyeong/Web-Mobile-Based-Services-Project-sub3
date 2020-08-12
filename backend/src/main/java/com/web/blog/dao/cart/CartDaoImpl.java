package com.web.blog.dao.cart;

import java.util.List;

import com.web.blog.dto.cart.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDaoImpl implements CartDao {

	@Autowired
	CartMapper cartMapper;

	@Override
	public int registCart(Cart cart) {
		return cartMapper.registCart(cart);
	}

	@Override
	public int deleteCart(int queNo) {
		return cartMapper.deleteCart(queNo);
	}

	@Override
	public List<Integer> cartList(int userNo) {
		return cartMapper.cartList(userNo);
	}

}