package com.suyash.springlearning.repository;
import com.suyash.springlearning.entity.ConfigEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigEntryRepository extends MongoRepository<ConfigEntity , ObjectId> {
}
