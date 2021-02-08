package com.alan.lingua.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("word")
public class Word {
    @Id
    private Long id;
    @Column("language_id")
    private Long languageId;
    @Column
    private String text;
}
