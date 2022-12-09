create database test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use test;

create table user(
	ID varchar(10) primary key,
    pass varchar(10)
);


delimiter $$
create procedure getUser(in name varchar(10))
begin
	select * from user where id like
     CONCAT('%', name , '%');
end $$

insert into user values('PS01','12345');
insert into user values('PS02','159357');
insert into user values('PS03','123');
