DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS seats cascade;

create table users(
	id                    BIGSERIAL NOT NULL PRIMARY KEY,
	name                  varchar(50),
	phone                 varchar(20),
	email                 varchar(50),
	confirmation_code     varchar(50)
);

create table seats(
	id                    BIGSERIAL NOT NULL PRIMARY KEY,
	user_id               BIGINT,
	row_number            int  not null,
	seat_number           int  not null,
 	status                int not null default 0,  /*0=Available, 1=OnHold, 2=Reserved*/
	FOREIGN KEY (user_id) references users(id)
);



