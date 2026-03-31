package com.bobby.onlinestore.repositories;

import com.bobby.onlinestore.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}