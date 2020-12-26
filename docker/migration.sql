create table users
(
    userid       serial primary key,
    username varchar(50) unique not null
);

select * from users;

insert into users(userid,username) values (1,'Вася');
insert into users(userid,username) values (2,'Петя');
insert into users(userid,username) values (3,'Галя');

drop table users