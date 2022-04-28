create table companies (
	id				integer primary key autoincrement,
	company_name	text
);

create table users (
	id				integer primary key autoincrement,
	name 			text not null,
	age				integer not null,
	company_id		integer not null,
	foreign key (company_id) references companies (id)
);

insert into companies values
(?, 'lerya'),
(?, 'eldorado'),
(?, 'lenta');

insert into users values
(?, 'ivan', 19, 1),
(?, 'alex', 20, 2),
(?, 'pasha', 17, 3)