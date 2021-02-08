package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("person")
public class Person {
    @Id
    private Long id;
    @Column
    private String name;
    @Column(value = "password_hash")
    private String passwordHash;
}
