--liquibase formatted sql

--changeset kuznetsovanaya:create_table_user
create table users
(
    chat_id bigint primary key,
    name    varchar(255)
);

--changeset kuznetsovanaya:create_table_message
create table message
(
    id      bigserial primary key,
    message text,
    data    timestamp,
    user_id bigint not null,
    foreign key (user_id) references users (chat_id)
);