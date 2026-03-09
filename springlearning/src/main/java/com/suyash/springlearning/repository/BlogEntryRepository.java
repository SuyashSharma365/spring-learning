package com.suyash.springlearning.repository;

import com.suyash.springlearning.entity.BlogEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogEntryRepository extends MongoRepository<BlogEntity , ObjectId> {
}
