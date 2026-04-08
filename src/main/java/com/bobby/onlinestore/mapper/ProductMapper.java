package com.bobby.onlinestore.mapper;

import com.bobby.onlinestore.Dtos.ProductDto;
import com.bobby.onlinestore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);
    Product toEntity(ProductDto productDto);
    @Mapping(target = "id", ignore = true)
    void updateProduct(ProductDto productDto, @MappingTarget Product product);
}
