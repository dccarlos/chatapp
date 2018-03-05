SELECT * FROM chat.message;

select distinct chat.message.idmessage, chat.message.content,chat.group.name 
from chat.message inner join chat.group on chat.message.idgroup=chat.group.idgroup and chat.message.idgroup=2;


select distinct  chat.user.name,chat.user.mail
from chat.user_group inner join chat.user on chat.user.iduser=chat.user_group.iduser_user_group 
and chat.user_group.idgroup_user_group=1;


select distinct  chat.group.name,chat.group.creation
from chat.user_group inner join chat.group on chat.group.idgroup=chat.user_group.idgroup_user_group 
and chat.user_group.iduser_user_group=2;










select distinct chat.user.name ,chat.user.mail
from chat.user inner join chat.user_group on chat.user_group.idgroup_user_group=1;











SELECT * FROM chat.user inner join chat.user_group on chat.user_group.iduser_group=1;