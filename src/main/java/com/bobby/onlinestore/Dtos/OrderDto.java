package com.bobby.onlinestore.Dtos;

import com.bobby.onlinestore.entities.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private String Id;
    private String Status;
    private LocalDateTime CreatedAt;
    List<OrderItemDto> items;
    private BigDecimal totalPrice;


}
