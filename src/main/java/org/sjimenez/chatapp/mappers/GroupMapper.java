package org.sjimenez.chatapp.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.Message;
import org.sjimenez.chatapp.model.User;
import org.sjimenez.chatapp.model.UserGroupRelation;

import java.util.List;

@Mapper
public interface GroupMapper {
    Group selectGroupById(int idgroup);

    Group selectGroupByName(String name);

    List<Group> selectAll();

    List<UserGroupRelation> selectAllUG();

    void insertGroup(Group group);

    void updateGroup(@Param("newName")String newName,@Param("oldName")String oldName);

    void deleteGroupById(int idgroup);

    UserGroupRelation selectUserGroupRelation(@Param("idgroup")int idgroup,@Param("iduser")int iduser);

    void insertUserGroupRelation(@Param("idgroup_user_group")int idgroup,@Param("iduser_user_group")int iduser);

    void deleteUserFromGroup(@Param("idgroup")int idgroup,@Param("iduser")int iduser);

    List<Message> selectMessagesFromGroup(int idgroup);
    //////
    User selectUserForGroup(int iduser);
}
