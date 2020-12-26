package com.example.springdatamongoweb.rest;

import com.example.springdatamongoweb.mobgorepository.MongoUserRepository;
import com.example.springdatamongoweb.model.User;
import com.example.springdatamongoweb.repository.UserRepository;
import com.example.springdatamongoweb.repository2.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class SimpleRestController {

    @Autowired
    private  UserRepository repository;
    @Autowired
    private  UserRepository2 repository2;
    @Autowired
    private  MongoUserRepository mongoUserRepository;

    @GetMapping("/users")
    List<User> all() {
        List<User> usersResult = new ArrayList<>();
        usersResult.addAll((Collection<? extends User>) repository.findAll());
        usersResult.addAll((Collection<? extends User>) repository2.findAll());
        mongoUserRepository.findAll();
        return usersResult;
    }
}
