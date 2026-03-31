package com.bobby.onlinestore.repositories;

import com.bobby.onlinestore.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}