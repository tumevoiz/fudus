CREATE TABLE category (
    uuid uuid primary key not null ,
    name text not null ,
    icon varchar(8) not null
);

CREATE TABLE credentials (
    uuid uuid primary key not null ,
    username varchar(30) unique not null ,
    password varchar(50) not null
);