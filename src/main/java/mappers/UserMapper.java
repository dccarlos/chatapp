package mappers;
import model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users")
    List<User> findAll();

    @Options(useGeneratedKeys = true, keyProperty = "iduser", keyColumn = "iduser")
    @Insert("insert into user(name,lastnName,mail,nickName,birthdate) values(#{name},#{lastName},#{email},#{nickname},#{birthdate})")
    int insert(User user);


    @Update("TRUNCATE TABLE users; ALTER TABLE user ALTER COLUMN iduser RESTART WITH 1")
    void truncateTableUsers();

}
