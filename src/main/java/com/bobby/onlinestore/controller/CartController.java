package com.bobby.onlinestore.controller;

import com.bobby.onlinestore.Dtos.AddProductRequst;
import com.bobby.onlinestore.Dtos.CartDto;
import com.bobby.onlinestore.Dtos.CartItemsDto;
import com.bobby.onlinestore.Dtos.UpdateCartItemDto;
import com.bobby.onlinestore.entities.Cart;
import com.bobby.onlinestore.entities.CartItem;
import com.bobby.onlinestore.mapper.CartMapper;
import com.bobby.onlinestore.repositories.CartRepository;
import com.bobby.onlinestore.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toDto(cart);
        var uri =   uriBuilder.path("/carts/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemsDto> addToCart(@PathVariable UUID cartId, @RequestBody AddProductRequst request) {
        var cart  = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cartMapper.toDto(cart));
        
    }

    public ResponseEntity<?> updateCart(@PathVariable("cartId") UUID cartId,
                                        @PathVariable("productId") Long productId,
                                        @Valid @RequestBody UpdateCartItemDto request) {

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var cartItem = cart.getItem(productId);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return ResponseEntity.ok(cartMapper.toDto(cartItem));


    }

}
