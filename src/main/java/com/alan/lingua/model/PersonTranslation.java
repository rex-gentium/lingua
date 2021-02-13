package com.alan.lingua.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("person_translations")
public class PersonTranslation {
    @Id
    private Long id;
    @Column("person_id")
    private Long personId;
    @Column("translation_id")
    private Long translationId;
}
