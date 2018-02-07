package org.sjimenez.chatapp.mappers;
import org.sjimenez.chatapp.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    @Options(useGeneratedKeys = true, keyProperty = "iduser", keyColumn = "iduser")
    @Insert("insert into user(name,lastName,mail,nickName,birthdate) values(#{name},#{lastName},#{mail},#{nickname},#{birthdate})")
    int insert(User user);


    @Update("TRUNCATE TABLE user; ALTER TABLE user ALTER COLUMN iduser RESTART WITH 1")
    void truncateTableUsers();

}
