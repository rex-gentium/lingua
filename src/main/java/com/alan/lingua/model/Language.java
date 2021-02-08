package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("language")
public class Language {
    @Id
    private Long id;
    @Column
    private String code;
    @Column
    private String name;
}
