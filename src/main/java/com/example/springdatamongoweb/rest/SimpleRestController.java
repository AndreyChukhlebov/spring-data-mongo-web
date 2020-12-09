package com.example.springdatamongoweb.rest;

import com.example.springdatamongoweb.mobgorepository.MongoUserRepository;
import com.example.springdatamongoweb.model.User;
import com.example.springdatamongoweb.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class SimpleRestController {

    private final UserRepository repository;
    private final MongoUserRepository mongoUserRepository;

    SimpleRestController(UserRepository repository, MongoUserRepository mongoUserRepository) {
        this.repository = repository;
        this.mongoUserRepository = mongoUserRepository;
    }

    @GetMapping("/employees")
    List<User> all() {
        List<User> usersResult = new ArrayList<>();
        usersResult.addAll((Collection<? extends User>) repository.findAll());
        mongoUserRepository.findAll();
        return usersResult;
    }
}
