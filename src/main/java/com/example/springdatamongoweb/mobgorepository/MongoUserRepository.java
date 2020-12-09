package com.example.springdatamongoweb.mobgorepository;

import com.example.springdatamongoweb.model.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<UserMongo, Long> {
}
