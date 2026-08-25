package com.bobby.onlinestore.controller;
import com.bobby.onlinestore.Dtos.CheckOutRequest;
import com.bobby.onlinestore.Dtos.CheckOutResponse;
import com.bobby.onlinestore.Dtos.ErrorDto;
import com.bobby.onlinestore.exceptions.CartEmptyException;
import com.bobby.onlinestore.exceptions.CartNotFoundException;
import com.bobby.onlinestore.exceptions.PaymentException;
import com.bobby.onlinestore.repositories.OrderRepository;
import com.bobby.onlinestore.services.CheckOutService;
import com.bobby.onlinestore.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService checkOutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public CheckOutResponse checkout(@Valid @RequestBody CheckOutRequest checkoutRequest){
        return checkOutService.checkout(checkoutRequest);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ) {
        checkOutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error Creating Checkout Session"));

    }
}
