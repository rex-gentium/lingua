create table category_translations (
    id bigserial primary key,
    category_id bigint not null,
    translation_id bigint not null,

    constraint category_translations_category_fk
        foreign key (category_id)
            references category(id)
            on update cascade
            on delete cascade,
    constraint category_translations_translation_fk
        foreign key (translation_id)
            references translation(id)
            on update cascade
            on delete cascade,
    constraint category_translations_category_translation_pk
        unique (category_id, translation_id)
);