package com.bobby.onlinestore.mapper;

import com.bobby.onlinestore.Dtos.OrderDto;
import com.bobby.onlinestore.entities.Order;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
