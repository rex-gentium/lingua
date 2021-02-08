create table language (
  id bigserial primary key,
  code varchar(10) not null,
  name varchar(32) not null,

  constraint language_code_pk
      unique(code)
);

insert into language(code, name) values
  ('en', 'Английский'),
  ('ru', 'Русский');

