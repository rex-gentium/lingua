package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("translation_view")
public class TranslationView {
    @Id
    private Long id;
    @Column("source_word_id")
    private Long sourceWordId;
    @Column("target_word_id")
    private Long targetWordId;
    @Column("author_id")
    private Long authorId;
    @Column("source_language_id")
    private Long sourceLanguageId;
    @Column("target_language_id")
    private Long targetLanguageId;
    @Column("source_word_text")
    private String sourceWordText;
    @Column("target_word_text")
    private String targetWordText;
    @Column("author_name")
    private String authorName;
}
