--Inserts in users table
INSERT INTO `user`(name,lastName,mail,nickname,birthdate) VALUES('name1','last1','ss1@ss1.com','nick1','1999-05-05');
INSERT INTO `user`(name,lastName,mail,nickname,birthdate) VALUES('name2','last2','ss2@ss2.com','nick2','1999-05-05');
INSERT INTO `user`(name,lastName,mail,nickname,birthdate) VALUES('name3','last3','ss3@ss3.com','nick3','1999-05-05');
INSERT INTO `user`(name,lastName,mail,nickname,birthdate) VALUES('name4','last4','ss4@ss4.com','nick4','1999-05-05');
INSERT INTO `user`(name,lastName,mail,nickname,birthdate) VALUES('name5','last5','ss5@ss5.com','nick5','1999-05-05');

--Inserts in group table
INSERT INTO `group`(name,creation) VALUES('groupname1','1999-05-05');
INSERT INTO `group`(name,creation) VALUES('groupname2','1999-05-05');
INSERT INTO `group`(name,creation) VALUES('groupname3','1999-05-05');


--Inserts in message table
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message1','1999-05-05',1);
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message1','1999-05-05',2);
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message1','1999-05-05',3);
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message2','1999-05-05',1);
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message2','1999-05-05',2);
INSERT INTO `message`(content,creation,idgroup_message) VALUES('message2','1999-05-05',3);

--Inserts in user_group

INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(1,2);
INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(1,1);

INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(2,1);
INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(2,2);
INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(2,3);

INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(3,1);
INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(3,2);
INSERT INTO `user_group`(idgroup_user_group,iduser_user_group) VALUES(3,3);