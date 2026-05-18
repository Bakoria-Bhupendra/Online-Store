package com.bobby.onlinestore.controller;

import com.bobby.onlinestore.Dtos.AddProductRequst;
import com.bobby.onlinestore.Dtos.CartDto;
import com.bobby.onlinestore.Dtos.CartItemsDto;
import com.bobby.onlinestore.Dtos.UpdateCartItemDto;
import com.bobby.onlinestore.exceptions.CartNotFoundException;
import com.bobby.onlinestore.exceptions.ProductNotFoundException;
import com.bobby.onlinestore.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.cartCreate();
        var uri =   uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemsDto> addToCart(@PathVariable UUID cartId, @RequestBody AddProductRequst request) {
        var cartItemDto = cartService.addCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        return cartService.gettingCart(cartId);

        
    }
    @PutMapping("{cartId}/items/{productId}")
    public CartItemsDto updateCart(@PathVariable("cartId") UUID cartId,
                                        @PathVariable("productId") Long productId,
                                        @Valid @RequestBody UpdateCartItemDto request) {

        return cartService.updatingCart(cartId, productId, request.getQuantity());



    }
    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId) {
        cartService.removingCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearingCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in cart"));
    }
    

}
