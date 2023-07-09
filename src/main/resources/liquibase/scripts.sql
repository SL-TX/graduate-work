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
create unique index user_email_pkey
    on "user" (email); -- Username
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
    created_at timestamp
);
create table image_entity
(
    id    uuid not null
        primary key,
    image oid
);
