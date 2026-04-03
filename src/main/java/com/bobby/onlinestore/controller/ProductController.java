package com.bobby.onlinestore.controller;

import com.bobby.onlinestore.Dtos.ProductDto;
import com.bobby.onlinestore.entities.Category;
import com.bobby.onlinestore.entities.Product;
import com.bobby.onlinestore.mapper.ProductMapper;
import com.bobby.onlinestore.repositories.CategoryRepository;
import com.bobby.onlinestore.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(required = false, name = "categoryId") Byte categoryId) {
        List<Product> products;
        if  (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }
        return products
                .stream()
                .map(productMapper::toProductDto).toList();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long Id) {
        var product = productRepository.findById(Id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toProductDto(product));
    }
}
