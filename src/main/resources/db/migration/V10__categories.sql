create table category (
    id bigserial primary key,
    name varchar(255) not null,
    person_id bigint not null,

    constraint category_person_fk
        foreign key (person_id)
            references person(id)
            on update cascade
            on delete cascade,
    constraint category_name_person_pk
        unique (name, person_id)
);