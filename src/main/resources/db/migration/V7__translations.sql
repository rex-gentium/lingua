create table translation (
    id bigserial primary key,
    source_word_id bigint not null,
    target_word_id bigint not null,
    author_id bigint,

    constraint translation_source_word_fk
        foreign key (source_word_id)
            references word(id)
            on delete cascade
            on update cascade,
    constraint translation_target_word_fk
        foreign key (target_word_id)
            references word(id)
            on delete cascade
            on update cascade,
    constraint translation_person_fk
        foreign key (author_id)
            references person(id)
            on delete set null
            on update cascade,
    constraint translation_pair_pk
        unique (source_word_id, target_word_id)
);

create unique index translation_sideway_idx
    on translation(greatest(source_word_id,target_word_id), least(source_word_id,target_word_id));