package com.bobby.onlinestore.services;

import com.bobby.onlinestore.entities.Order;
import com.bobby.onlinestore.entities.OrderItem;
import com.bobby.onlinestore.entities.PaymentStatus;
import com.bobby.onlinestore.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway{
    @Value("${websiteURL}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;


    @Override
    public CheckoutSession checkoutSession(Order order) {
       try {
           var builder = SessionCreateParams.builder()
                   .setMode(SessionCreateParams.Mode.PAYMENT)
                   .setSuccessUrl(websiteUrl + "checkout-success?orderId=" + order.getId())
                   .setCancelUrl(websiteUrl + "checkout-cancel")
                   .putMetadata("order_id", order.getId().toString());

           order.getItems().forEach(item -> {
               var lineItem = createLineItem(item);
               builder.addLineItem(lineItem);
           });

           var session = Session.create(builder.build());
           return new CheckoutSession(session.getUrl());

       }
       catch (StripeException ex) {
           throw new PaymentException();
       }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {

        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            if (event.getType().equals("payment_intent_succeeded")) {
                return Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));
            }

            else if (event.getType().equals("payment_intent.payment_failed")) {
                return Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));
            }

            else {
                return Optional.empty();
            }

        } catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid signature");
        }

    }

    public Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event. Check the API and SDK version.")
        );
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));

    }

    private static SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("USD")
                .setUnitAmountDecimal
                        (item.getUnit().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
