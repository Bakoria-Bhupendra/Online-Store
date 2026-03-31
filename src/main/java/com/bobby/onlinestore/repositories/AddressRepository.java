package com.bobby.onlinestore.repositories;

import com.bobby.onlinestore.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}