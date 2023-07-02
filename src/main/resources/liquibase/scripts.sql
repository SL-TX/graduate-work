create table "user"
(
    id         serial
        primary key,
    first_name varchar(255),
    last_name  varchar(255),
    email      varchar(255),
    phone      varchar(255),
    image      varchar(255),
    password   varchar(255),
    role       varchar(255),
    enabled    boolean
);
create table ads
(
    pk          serial
        primary key,
    author_id   integer
        constraint user_reference
            references "user",
    price       integer,
    title       varchar(255),
    description text,
    image       varchar(255)
);
create table comment
(
    pk         serial
        primary key,
    ads_pk     integer
        constraint ads_reference
            references ads,
    user_id    integer
        constraint user_reference
            references "user",
    text       text,
    created_at bigint
);

INSERT INTO "user" (id, email, first_name, image, last_name, password, phone, role, enabled)
VALUES (DEFAULT, 'test@test.tt', 'first_name',
        'https://mykaleidoscope.ru/uploads/posts/2022-06/1656422760_27-mykaleidoscope-ru-p-svetlo-rizhie-volosi-devushka-krasivo-foto-28.jpg',
        'last_name', '$2a$10$nfA.RQAuHkE/HqDA6H3x4u5fBVfI2iq2RDWEyGLf9AqBxsJu4OObW', 'test', 'USER', true);

INSERT INTO ads (pk, description, image, price, title, author_id)
VALUES (DEFAULT, 'Many many row text',
        'https://mykaleidoscope.ru/uploads/posts/2022-06/1656422760_27-mykaleidoscope-ru-p-svetlo-rizhie-volosi-devushka-krasivo-foto-28.jpg',
        1231, 'Title', 1);

