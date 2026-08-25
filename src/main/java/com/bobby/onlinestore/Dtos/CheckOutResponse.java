package com.bobby.onlinestore.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CheckOutResponse {
    private Long orderId;
    private String url;

    public  CheckOutResponse(Long orderId, String url) {

        this.orderId = orderId;
        this.url = url;
    }

}
