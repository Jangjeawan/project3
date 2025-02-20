create table MEMBERS(
	MEMBERID varchar(50) primary key,
	MEMBERPASSWORD varchar(255) not null
);

create table MOIM(
	MOIMID int auto_increment primary key,
	MOIMNAME varchar(100) not null
);

create table MEMBERSCONTACTS(
	CONTACTID int auto_increment primary key,
	NAME varchar(100) not null,
	PHONENUMBER varchar(20) unique not null,
	ADDRESS varchar(255),
	MOIMID int,
	PHOTO varchar(255),
	REGDT timestamp default current_timestamp,
	foreign key(MOIMID) references MOIM(MOIMID)
);

CREATE TABLE MEMBERCONTACTMAP (
    MEMBERID VARCHAR(50),
    CONTACTID INT,
    PRIMARY KEY (MEMBERID, CONTACTID),
    FOREIGN KEY (MEMBERID) REFERENCES MEMBERS(MEMBERID) ON DELETE CASCADE,
    FOREIGN KEY (CONTACTID) REFERENCES MEMBERSCONTACTS(CONTACTID) ON DELETE CASCADE
);

INSERT INTO MOIM (MOIMID, MOIMNAME) VALUES 
(1, '가족'), 
(2, '친구'), 
(3, '회사'),
(4, '기타');

UPDATE MOIM
SET MOIMNAME = '가족'
WHERE MOIMID = 1;

UPDATE MOIM
SET MOIMNAME = '친구'
WHERE MOIMID = 2;

UPDATE MOIM
SET MOIMNAME = '회사'
WHERE MOIMID = 3;

UPDATE MOIM
SET MOIMNAME = '기타'
WHERE MOIMID = 4;

ALTER TABLE members DROP COLUMN member_id, DROP COLUMN member_password;