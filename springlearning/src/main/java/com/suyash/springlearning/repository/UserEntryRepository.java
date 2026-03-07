package com.suyash.springlearning.repository;

import com.suyash.springlearning.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntity , String> {
}
