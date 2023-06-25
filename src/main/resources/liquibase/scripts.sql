create table "user"
(
    id         serial
        primary key,
    avatar     varchar(255),
    email      varchar(255),
    first_name varchar(255),
    image      varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    phone      varchar(255),
    role       integer
);
create table ads
(
    pk          serial
        primary key,
    description varchar(255),
    image       varchar(255),
    price       integer,
    title       varchar(255),
    author_id   integer
        constraint user_reference
            references "user"
);
create table comment
(
    pk         serial
        primary key,
    created_at bigint,
    text       varchar(255),
    ads_pk     integer
        constraint ads_reference
            references ads,
    user_id    integer
        constraint user_reference
            references "user"
);