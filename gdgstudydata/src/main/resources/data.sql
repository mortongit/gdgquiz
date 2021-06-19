DROP TABLE IF EXISTS music;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE music (
	id INT AUTO_INCREMENT  PRIMARY KEY,
	title VARCHAR(250) NOT NULL,
	artist VARCHAR(250) NOT NULL,
	regdate DATE NOT NULL,
	url VARCHAR(250) DEFAULT NULL
);

INSERT INTO music (title, artist, regdate, url) VALUES
	('그때의 우린', '스페이스카우보이', '2019-01-17', 'https://youtu.be/s3HOnx6NNpY'),
	('아무것도 아니야', '뮤지', '2018-08-27', 'https://youtu.be/DN20QSP3CqI'),
	('떠나보낼 수 없어', '뮤지', '2018-03-12', 'https://youtu.be/1Rg3UgcIhpk');

	
create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(50) not null,
	enabled boolean not null
);

insert into users
(username,password,enabled) 
values
('a001','1234',TRUE),
('a002','1234',TRUE);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into authorities values
	('a001', 'ROLE_USER'),
	('a002', 'ROLE_ADMIN'),
	('a002', 'ROLE_MANAGER');

--CREATE TABLE member ( 
--id INT AUTO_INCREMENT  PRIMARY KEY,
--mem_id VARCHAR(50) ,
--mem_pass VARCHAR(50),
--mem_name VARCHAR(50),
--UNIQUE KEY member_mem_id_UNIQUE (mem_id) 
--);

-- ALTER TABLE member ADD CONSTRAINT MEM_ID_UNIQUE UNIQUE(mem_id));

--insert into member
--(mem_id,mem_pass,mem_name) 
--values
--('a001','1234','고길동'),
--('a002','1234','마이콜');

--create table authorities (
--username VARCHAR(50) not null,
--authority VARCHAR(50) not null,
--constraint fk_authorities_users foreign key(username) references member(mem_id)
--);

--create unique index ix_auth_username on authorities (username,authority);

--insert into authorities values
--	('a001', 'ROLE_USER'),
--	('a002', 'ROLE_ADMIN'),
--	('a002', 'ROLE_MANAGER');
