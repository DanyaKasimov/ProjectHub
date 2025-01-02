CREATE SCHEMA user_management;
CREATE SCHEMA email_management;
CREATE SCHEMA profile_management;


CREATE TABLE user_management.companies
(
    id         UUID default gen_random_uuid() NOT NULL,
    name       VARCHAR(255)                   NOT NULL,
    inn        VARCHAR(10)                    NOT NULL,
    domain     VARCHAR(15)                    NOT NULL,
    created_at TIMESTAMP                      NOT NULL,
    primary key (id)
);

CREATE TABLE email_management.emails
(
    id         UUID default gen_random_uuid() NOT NULL,
    name       VARCHAR(255)                   NOT NULL,
    created_at TIMESTAMP                      NOT NULL,
    primary key (id)
);


CREATE TABLE email_management.email_messages
(
    id         UUID default gen_random_uuid() NOT NULL,
    from_id    UUID                           NOT NULL,
    to_id      UUID                           NOT NULL,
    title      VARCHAR(255),
    body       VARCHAR(1000),
    created_at TIMESTAMP                      NOT NULL,
    primary key (id),
    foreign key (from_id) references email_management.emails (id),
    foreign key (to_id) references email_management.emails (id)
);


CREATE TABLE user_management.users
(
    id         UUID default gen_random_uuid() NOT NULL,
    name       VARCHAR(255)                   NOT NULL,
    surname    VARCHAR(255)                   NOT NULL,
    patronymic VARCHAR(255)                   NOT NULL,
    username   VARCHAR(255)                   NOT NULL,
    password   VARCHAR(255)                   NOT NULL,
    email_id   UUID                           NOT NULL,
    email_root VARCHAR(255)                   NOT NULL,
    position   VARCHAR(255)                   NOT NULL,
    company_id UUID,
    role       VARCHAR(255),
    created_at TIMESTAMP                      NOT NULL,
    updated_at TIMESTAMP                      NOT NULL,
    primary key (id),
    foreign key (company_id) references user_management.companies (id),
    foreign key (email_id) references email_management.emails (id)
);


CREATE TABLE profile_management.info
(
    id           UUID default gen_random_uuid() NOT NULL,
    user_id      UUID                           NOT NULL,
    phone_number VARCHAR(1000),
    address      VARCHAR(300),
    birthday     TIMESTAMP,
    created_at   TIMESTAMP                      NOT NULL,
    updated_at   TIMESTAMP                      NOT NULL,
    primary key (id),
    foreign key (user_id) references user_management.users (id)
);


CREATE TABLE profile_management.avatars
(
    id         UUID default gen_random_uuid() NOT NULL,
    info_id    uuid                           not null,
    url        VARCHAR(1000)                  NOT NULL,
    created_at TIMESTAMP                      NOT NULL,
    updated_at TIMESTAMP                      NOT NULL,
    primary key (id),
    foreign key (info_id) references profile_management.info (id)
);


