package org.sjimenez.chatapp.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.User;

@Mapper
public interface GroupMapper {

    User selectUserByIdd(int iduser);
    Group selectGroupById(int idgroup);


}
