create table person_translations (
    id bigserial primary key,
    person_id bigint not null,
    translation_id bigint not null,

    constraint person_translations_person_fk
        foreign key (person_id)
            references person(id)
            on update cascade
            on delete cascade,
    constraint person_translations_translation_fk
        foreign key (translation_id)
            references translation(id)
            on update cascade
            on delete cascade,
    constraint person_translations_pk
        unique (person_id, translation_id)
);

insert into person_translations (person_id, translation_id)
select t.author_id as person_id, t.id as translation_id
from translation t
where t.author_id is not null;
