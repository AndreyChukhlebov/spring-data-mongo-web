package com.example.springdatamongoweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@Table("users")
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private long userid;
    private String username;
}
