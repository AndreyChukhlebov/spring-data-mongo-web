package com.example.springdatamongoweb.repository2;

import com.example.springdatamongoweb.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends CrudRepository<User,Long> {
}
