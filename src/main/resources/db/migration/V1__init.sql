create table "user" (
  id bigserial primary key,
  name varchar(255) not null,
  password_hash text not null,

  constraint user_name_pk
      unique(name)
);

insert into "user" (name, password_hash) values ('alan', crypt('3285', gen_salt('bf', 8)));