CREATE TABLE IF NOT EXISTS user (
    iduser INT PRIMARY KEY auto_increment ,
    name VARCHAR,
    lastName VARCHAR,
    mail VARCHAR UNIQUE ,
    nickName VARCHAR,
    birthdate DATE
);
