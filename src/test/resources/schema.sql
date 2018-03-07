
CREATE SCHEMA IF NOT EXISTS `chat`;
USE `chat` ;



CREATE TABLE IF NOT EXISTS `user` (
    iduser INT PRIMARY KEY auto_increment ,
    name VARCHAR,
    lastName VARCHAR,
    mail VARCHAR UNIQUE ,
    nickName VARCHAR,
    birthdate DATE
);

CREATE TABLE IF NOT EXISTS `groupp` (
  idgroup INT PRIMARY KEY auto_increment,
  name VARCHAR(45) NULL,
 creation DATE NULL
 );




-- -----------------------------------------------------
-- Table `chat`.`user`
-- -----------------------------------------------------
--CREATE TABLE IF NOT EXISTS user (
  --iduser INT(11) NOT NULL AUTO_INCREMENT,
 -- name VARCHAR(45) NOT NULL,
  --lastName VARCHAR(45) NOT NULL,
 -- mail VARCHAR(45) NOT NULL,
 -- nickname TEXT NOT NULL,
 -- birthdate DATE NOT NULL,
  --PRIMARY KEY (iduser),
 -- UNIQUE INDEX `maill_UNIQUE` (`mail` ASC));


-- -----------------------------------------------------
-- Table `chat`.`group`
-- -----------------------------------------------------
--CREATE TABLE IF NOT EXISTS group (
--  idgroup INT NOT NULL,
 --name VARCHAR(45) NOT NULL,
--  creation DATE NOT NULL,
--PRIMARY KEY (idgroup));


--CREATE TABLE IF NOT EXISTS `Person` (
  --  `id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
   --  `first_name` VARCHAR(50) NOT NULL,
   --  `age`        INTEGER  NOT NULL
--);





/*
CREATE TABLE IF NOT EXISTS `groupp` (
  `idgroup` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
 `creation` DATE NULL,
  PRIMARY KEY (`idgroup`));
*/



  CREATE TABLE IF NOT EXISTS `message` (
    `idmessage` INT NOT NULL AUTO_INCREMENT,
    `content` VARCHAR(255) NULL,
    `creation` DATE NULL,
    `idgroup_message` INT NOT NULL,
    PRIMARY KEY (`idmessage`),
    CONSTRAINT `fk_idgroup_message`
      FOREIGN KEY (`idgroup_message`)
      REFERENCES `groupp` (`idgroup`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);
--  INDEX `fk_idgroup_idx` (`idgroup_message` ASC),
create INDEX `fk_idgroup_idx` on message(`idgroup_message` ASC);




  -- -----------------------------------------------------
  -- Table `chat`.`user_group`
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS `user_group` (
    `iduser_group` INT NOT NULL AUTO_INCREMENT,
    `idgroup_user_group` INT NOT NULL,
    `iduser_user_group` INT NOT NULL,
    PRIMARY KEY (`iduser_group`),
    CONSTRAINT `fk_idgroup_user_group`
      FOREIGN KEY (`idgroup_user_group`)
      REFERENCES `groupp` (`idgroup`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
    CONSTRAINT `fk_iduser_user_group`
      FOREIGN KEY (`iduser_user_group`)
      REFERENCES `user` (`iduser`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);


/*

      */

    --      INDEX `fk_idgroup_idx` (`idgroup_user_group` ASC),
    --    INDEX `fk_iduser_idx` (`iduser_user_group` ASC),


create INDEX `fk_idgroupug_idx` on user_group(`idgroup_user_group` ASC);
create INDEX `fk_iduser_idx` on user_group(`iduser_user_group` ASC);


--Inserts in users table
INSERT INTO user(name,lastName,mail,nickname,birthdate) VALUES('name1','last1','ss1@ss1.com','nick1','1999-05-05');
INSERT INTO user(name,lastName,mail,nickname,birthdate) VALUES('name2','last2','ss2@ss2.com','nick2','1999-05-05');
INSERT INTO user(name,lastName,mail,nickname,birthdate) VALUES('name3','last3','ss3@ss3.com','nick3','1999-05-05');
INSERT INTO user(name,lastName,mail,nickname,birthdate) VALUES('name4','last4','ss4@ss4.com','nick4','1999-05-05');
INSERT INTO user(name,lastName,mail,nickname,birthdate) VALUES('name5','last5','ss5@ss5.com','nick5','1999-05-05');

--Inserts in group table
INSERT INTO groupp(name,creation) VALUES('groupname1','1999-05-05');
INSERT INTO groupp(name,creation) VALUES('groupname2','1999-05-05');
INSERT INTO groupp(name,creation) VALUES('groupname3','1999-05-05');


--Inserts in message table
INSERT INTO message(content,creation,idgroup_message) VALUES('message1','1999-05-05',1);
INSERT INTO message(content,creation,idgroup_message) VALUES('message1','1999-05-05',2);
INSERT INTO message(content,creation,idgroup_message) VALUES('message1','1999-05-05',3);
INSERT INTO message(content,creation,idgroup_message) VALUES('message2','1999-05-05',1);
INSERT INTO message(content,creation,idgroup_message) VALUES('message2','1999-05-05',2);
INSERT INTO message(content,creation,idgroup_message) VALUES('message2','1999-05-05',3);

--Inserts in user_group
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(1,1);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(1,2);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(1,3);

INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(2,1);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(2,2);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(2,3);

INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(3,1);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(3,2);
INSERT INTO user_group(idgroup_user_group,iduser_user_group) VALUES(3,3);






