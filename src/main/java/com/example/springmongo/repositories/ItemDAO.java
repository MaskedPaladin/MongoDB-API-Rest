package com.example.springmongo.repositories;

import com.example.springmongo.model.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO extends MongoRepository<Item, ObjectId> {
}
