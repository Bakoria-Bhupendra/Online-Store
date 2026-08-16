package com.bobby.onlinestore.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutResponse {
    private Long orderId;

    public  CheckOutResponse(Long orderId) {
        this.orderId = orderId;
    }
}
