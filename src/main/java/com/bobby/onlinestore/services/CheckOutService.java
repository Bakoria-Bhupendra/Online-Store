package com.bobby.onlinestore.services;

import com.bobby.onlinestore.Dtos.CheckOutRequest;
import com.bobby.onlinestore.Dtos.CheckOutResponse;
import com.bobby.onlinestore.Dtos.ErrorDto;
import com.bobby.onlinestore.entities.Order;
import com.bobby.onlinestore.exceptions.CartEmptyException;
import com.bobby.onlinestore.exceptions.CartNotFoundException;
import com.bobby.onlinestore.repositories.CartRepository;
import com.bobby.onlinestore.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckOutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    public CheckOutResponse checkout(CheckOutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()){
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearingCart(cart.getId());

        return new CheckOutResponse(order.getId());    }
}
