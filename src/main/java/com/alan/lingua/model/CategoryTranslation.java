package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("category_translations")
public class CategoryTranslation {
    @Id
    private Long id;
    @Column("category_id")
    private Long categoryId;
    @Column("translation_id")
    private Long translationId;
}
