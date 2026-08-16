package com.bobby.onlinestore.controller;

import com.bobby.onlinestore.Dtos.OrderDto;
import com.bobby.onlinestore.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }


    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        return null;
    }

}
