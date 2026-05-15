package com.bobby.onlinestore.Dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductRequst {
    @NotNull
    private Long productId;
}
