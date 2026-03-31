package com.bobby.onlinestore.repositories;

import com.bobby.onlinestore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}