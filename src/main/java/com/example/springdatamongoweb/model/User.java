package com.example.springdatamongoweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Table("users")
@Document(collection = "users")
public class User implements Serializable{
    @Id
    private Long userid;
    private String username;

}
