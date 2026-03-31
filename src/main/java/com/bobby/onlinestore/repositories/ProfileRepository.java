package com.bobby.onlinestore.repositories;

import com.bobby.onlinestore.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}