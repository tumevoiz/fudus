CREATE TABLE restaurant (
    uuid varchar(100) primary key unique ,
    slug varchar(50) unique ,
    name varchar(100) ,
    description text ,
    image_base64 text ,
    rating real
);

CREATE TABLE food (
    uuid varchar(100) primary key unique ,
    name varchar(100) ,
    categories varchar(100)[],
    price real,
    restaurant varchar(255)
);