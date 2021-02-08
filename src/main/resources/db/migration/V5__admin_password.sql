update person
set password_hash = concat('{bcrypt}', crypt('3285', gen_salt('bf', 10)))
where name = 'alan';