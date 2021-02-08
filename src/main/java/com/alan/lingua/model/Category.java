package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("category")
public class Category {
    @Id
    private Long id;
    @Column
    private String name;
    @Column("person_id")
    private Long personId;
}
