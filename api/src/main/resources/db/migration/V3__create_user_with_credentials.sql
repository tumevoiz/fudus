CREATE TABLE customer (
    uuid varchar(100) primary key unique ,
    email varchar(100) unique ,
    address text ,
    city varchar(50) ,
    role varchar(15)
);

CREATE TABLE credentials (
    uuid varchar(100) primary key unique ,
    username varchar(30) unique ,
    password varchar(50) unique ,
    customer varchar(100) unique
)