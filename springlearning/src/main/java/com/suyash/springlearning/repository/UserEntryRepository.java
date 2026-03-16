package com.suyash.springlearning.repository;

import com.suyash.springlearning.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntity , ObjectId> {
    UserEntity findByUserName(String username);

    void deleteByUserName(String username);
}
