package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("translation")
public class Translation {
    @Id
    private Long id;
    @Column("source_word_id")
    private Long sourceWordId;
    @Column("target_word_id")
    private Long targetWordId;
    @Column("author_id")
    private Long authorId;
}
