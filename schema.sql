\c weather_db;

CREATE TABLE users (
    ID SERIAL PRIMARY KEY,
    email VARCHAR UNIQUE,
    password VARCHAR
);

CREATE TABLE Locations (
    ID SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT REFERENCES users(ID),
    latitude DECIMAL,
    longitude DECIMAL
);

create table if not exists public.spring_session (
     primary_id char(36) not null,
     session_id char(36) not null,
     creation_time bigint not null,
     last_access_time bigint not null,
     max_inactive_interval int not null,
     expiry_time bigint not null,
     principal_name varchar(100),
     constraint spring_session_pk primary key (primary_id)
);

create unique index spring_session_ix1 on spring_session (session_id);
create index spring_session_ix2 on spring_session (expiry_time);
create index spring_session_ix3 on spring_session (principal_name);

create table if not exists public.spring_session_attributes (
    session_primary_id char(36) not null,
    attribute_name varchar(200) not null,
    attribute_bytes bytea not null,
    constraint spring_session_attributes_pk primary key (session_primary_id, attribute_name),
    constraint spring_session_attributes_fk foreign key (session_primary_id) references spring_session(primary_id) on delete cascade
);

-- DELETE FROM Sessions;
-- DELETE FROM Locations;
-- DELETE FROM User_Accounts;
--
-- DROP TABLE IF EXISTS Sessions;
-- DROP TABLE IF EXISTS Locations;
-- DROP TABLE IF EXISTS User_Accounts;


