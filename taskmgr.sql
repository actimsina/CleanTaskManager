create table users(id int auto_increment, name varchar(30) not null, primary key(id));

create table tasks(id int auto_increment, name varchar(100) not null, user_id int not null, primary key(id), foreign key(user_id) references users(id));

insert into users(name) values('Ram');
insert into tasks(name, user_id) values('Buy Fruits', 1);
insert into tasks(name, user_id) values('Call Shyam', 1);