package com.spring.dreamshop.service.cart;

import com.spring.dreamshop.dto.CartDto;
import com.spring.dreamshop.model.Cart;
import com.spring.dreamshop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    CartDto convertCartToDto(Cart cart);
}
