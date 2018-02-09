drop table if exists seat;
drop table if exists customer;

create table seat (
	seat_id int auto_increment primary key,
	seat_number tinyint not null,
	seat_row tinyint not null,
	seat_status varchar not null default 'available',
	customer_email varchar
);

create table customer (
	customer_id int auto_increment primary key,
	customer_email varchar not null
);