package com.bobby.onlinestore.services;

import com.bobby.onlinestore.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession checkoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);

}
