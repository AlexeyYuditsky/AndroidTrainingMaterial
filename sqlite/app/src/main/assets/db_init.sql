CREATE TABLE users(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	created_at text not null,
    name TEXT NOT NULL
);

insert into users (created_at, name) values
(datetime('now', 'localtime', '-5 days', '-3 hours', '-14 minutes'), 'Tom'),
(datetime('now', 'localtime'), 'Ivan')