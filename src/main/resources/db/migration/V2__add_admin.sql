INSERT INTO users (id, archive, email, name, password, role, bucket_id)
VALUES (1,false,'2.12nps@mail.ru','admin','pass','ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 2;