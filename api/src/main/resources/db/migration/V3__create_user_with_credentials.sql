CREATE TABLE "user" (
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
    "user" varchar(100) unique
)