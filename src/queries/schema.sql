create table users(
	id int auto_increment primary key,
	name varchar(30) not null	
);

create table tasks(
	id int auto_increment primary key,
	name varchar(50) not null,
	user_id int,
	foreign key(user_id) references users(id)
);

-- seed data
insert into users(name) values('acti10');
insert into tasks(name, user_id) values('Cook meal', 1);