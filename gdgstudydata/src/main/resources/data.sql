DROP TABLE IF EXISTS music;

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