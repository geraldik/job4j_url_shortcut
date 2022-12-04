CREATE TABLE IF NOT EXISTS url
(
    id            SERIAL PRIMARY KEY,
    calls_counter INT DEFAULT 0,
    long_url      TEXT NOT NULL UNIQUE,
    short_url     TEXT NOT NULL UNIQUE,
    site_id       INT  NOT NULL REFERENCES site (id)
);
comment on table url is 'Ссылки сайтов';
comment on column url.id is 'Идентификатор ссылки';
comment on column url.calls_counter is 'Количество вызовов ссылки';
comment on column url.long_url is 'Оригинальная ссылка';
comment on column url.short_url is 'Короткая ссылка';
comment on column url.site_id is 'Ссылка на зарегестрированный сайт';