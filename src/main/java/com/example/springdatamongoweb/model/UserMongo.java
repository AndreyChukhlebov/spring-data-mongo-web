package com.example.springdatamongoweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "users")
public class UserMongo implements Serializable {
    @Id
    private ObjectId _id;
    private long userid;
    private String username;
}
