package com.bobby.onlinestore.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
