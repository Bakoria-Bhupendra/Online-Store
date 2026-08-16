package com.bobby.onlinestore.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart Not Found");
    }
}
