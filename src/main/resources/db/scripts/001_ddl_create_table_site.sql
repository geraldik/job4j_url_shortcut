CREATE TABLE IF NOT EXISTS site
(
    id       SERIAL PRIMARY KEY,
    domain   TEXT NOT NULL UNIQUE,
    login    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL

);
comment on table site is 'Регистрируемый сайт';
comment on column site.id is 'Идентификатор сайта';
comment on column site.domain is 'Домен сайта';
comment on column site.login is 'Логин сайта';
comment on column site.password is 'Пароль сайта';
