package org.sjimenez.chatapp.test;


import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDbMapperTest {


    @Autowired
    private UserMapper userMapper;

    static private User user;

    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Before
    public void truncateTable() {
        logger.warn("BEFORE TEST");
       userMapper.truncateTableUsers();
    }
    @BeforeClass
    static public void initTestObjects(){
        Date date=new Date();
        System.out.println(date.toString());

        user=new User();
        user.setName("roshi");
        user.setLastName("kame");
        user.setMail("sjc@gmail.com");
        user.setNickname("jackiechun");
        user.setBirthdate(date);

    }

    
    @Test
    public void insert() {
        /*
        logger.warn("INSERT TEST");
        AppUser users = new AppUser();
        users.setUsername("jiren");
        users.setEmail("sjc");
        Integer i = userMapper.insert(users);
        System.out.println(i);
        System.out.println("isnertado" + String.valueOf(i));
        AppUser users2 = userMapper.selectUserLastRecord();
        System.out.println("id:" + users2.getIdusers());
        assertNotNull(users2);
        assertEquals("diferent chains", "jiren", users2.getUsername());
        */
    }








}
