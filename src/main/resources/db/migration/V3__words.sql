create table word (
  id bigserial primary key,
  text varchar(255) not null,
  language_id bigint not null,

  constraint word_language_fk
      foreign key(language_id)
          references language(id)
          on delete cascade
          on update cascade
);