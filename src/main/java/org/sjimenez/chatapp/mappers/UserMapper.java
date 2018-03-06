package org.sjimenez.chatapp.mappers;

        import org.sjimenez.chatapp.model.User;
        import org.apache.ibatis.annotations.*;

        import java.util.List;
        import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    @Options(useGeneratedKeys = true, keyProperty = "iduser", keyColumn = "iduser")
    @Insert("insert into user(name,lastName,mail,nickName,birthdate) values(#{name},#{lastName},#{mail},#{nickname},#{birthdate})")
    int insert(User user);

    @Delete("DELETE FROM user WHERE iduser =#{id}")
    int deleteUserById(int id);

    @Update("UPDATE user SET name=#{name},lastName=#{lastName},mail=#{mail},nickname=#{nickname},birthdate=#{birthdate} WHERE iduser =#{iduser}")
    int updateUser(User user);

    @Select("SELECT * FROM user ORDER BY iduser DESC LIMIT 1")
    User selectUserLastRecord();

    @Select("SELECT iduser,name,lastName,mail,nickName,birthdate from user WHERE iduser = #{iduser}")
    User selectUserById(int id);

    @Select("SELECT iduser,name,lastName,mail,nickName,birthdate from user WHERE mail = #{mail}")
    User selectUserByMail(String mail);

    @Update("SET FOREIGN_KEY_CHECKS = 0; TRUNCATE TABLE user; SET FOREIGN_KEY_CHECKS = 1; ALTER TABLE user ALTER COLUMN iduser RESTART WITH 1")
    void truncateTableUsers();

    @Update("TRUNCATE TABLE user; ALTER TABLE user ALTER COLUMN iduser RESTART WITH 1")
    void truncateTableUsers1();



    @Update("TRUNCATE TABLE user_group; ALTER TABLE user_group ALTER COLUMN iduser_group RESTART WITH 1;")
    void truncateTableUsersGroup();

}
