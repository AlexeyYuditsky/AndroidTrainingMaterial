PRAGMA foreign_keys = ON;

drop table if exists users;
drop table if exists companies;

create table if not exists companies(
	id integer primary key autoincrement,
	company_name text not null
);

create table if not exists users(
	id integer primary key autoincrement,
	name text not null,
	age integer,
	company_id integer,
	foreign key (company_id) references companies (id)
);

insert into companies values (?, "eldorado");
insert into companies values (?, "lerya");

insert into users values (?, "alex", 18, 1);
insert into users values (?, "ivan", 19, 2)
