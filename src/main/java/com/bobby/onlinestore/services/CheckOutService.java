package com.bobby.onlinestore.services;

import com.bobby.onlinestore.Dtos.CheckOutRequest;
import com.bobby.onlinestore.Dtos.CheckOutResponse;
import com.bobby.onlinestore.entities.Order;
import com.bobby.onlinestore.exceptions.CartEmptyException;
import com.bobby.onlinestore.exceptions.CartNotFoundException;
import com.bobby.onlinestore.exceptions.PaymentException;
import com.bobby.onlinestore.repositories.CartRepository;
import com.bobby.onlinestore.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;



    @Transactional
    public CheckOutResponse checkout(CheckOutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try {

            var session = paymentGateway.checkoutSession(order);

            cartService.clearingCart(cart.getId());

            return new CheckOutResponse(order.getId(), session.getCheckoutURL());

        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }



    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway.
                parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }
}
