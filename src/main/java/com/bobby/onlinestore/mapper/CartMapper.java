package com.bobby.onlinestore.mapper;

import com.bobby.onlinestore.Dtos.CartDto;
import com.bobby.onlinestore.Dtos.CartItemsDto;
import com.bobby.onlinestore.entities.Cart;
import com.bobby.onlinestore.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemsDto toDto(CartItem cartItem);
}
