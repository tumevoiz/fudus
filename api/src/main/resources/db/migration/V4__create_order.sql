CREATE TABLE ordering (
   uuid varchar(100) primary key unique ,
   ordered_by varchar(100),
   basket text[],
   creation_date varchar(100),
   has_paid boolean
);