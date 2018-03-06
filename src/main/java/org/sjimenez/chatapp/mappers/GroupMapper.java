package org.sjimenez.chatapp.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.sjimenez.chatapp.model.User;

@Mapper
public interface GroupMapper {
    User selectUserById(int iduser);
}
