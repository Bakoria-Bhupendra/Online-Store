package com.bobby.onlinestore.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsDto {
    private CartProductItemDto product;
    private int quantity;
    private BigDecimal totalPrice;
}

