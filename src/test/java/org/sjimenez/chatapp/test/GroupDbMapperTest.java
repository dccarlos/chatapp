package org.sjimenez.chatapp.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.mappers.GroupMapper;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupDbMapperTest {
    private User user;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Before
    public void initUnitTest() {
        userMapper.truncateTableUsersGroup();
        userMapper.truncateTableUsers();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2005-06-12", formatter);
        user = new User();
        user.setName("roshi");
        user.setLastName("kame");
        user.setMail("sjc@gmail.com");
        user.setNickname("jackiechun");
        user.setBirthdate(date);
    }

    @Test
    public void nosense(){




    }





/*
    @Test
    public void insertUser() {
        logger.info("Insert user test");
        userMapper.insert(user);
        List<User> list = userMapper.findAll();
        assertEquals("Compare returned id", 1, user.getIduser());
        assertEquals("total rows", 1, list.size());
    }

    @Test
    public void deleteUserById_() {
        logger.info("Delete user test");
        userMapper.insert(user);
        userMapper.deleteUserById(1);
        List<User> list = userMapper.findAll();
        assertEquals("empty", 0, list.size());
    }

    @Test
    public void updateUserById() {
        logger.info("Update user test");
        userMapper.insert(user);
        user.setName("jire");
        userMapper.updateUser(user);
        List<User> list = userMapper.findAll();
        assertEquals("Compare returned id", "jire", list.get(0).getName());
        assertEquals("total rows", 1, list.size());
    }

    @Test
    public void selectUserById() {
        logger.info("Select user by id test");
        userMapper.insert(user);
        User selectedUser = userMapper.selectUserById(1);
        assertEquals("Equal objects", user, selectedUser);
    }

    @Test
    public void selectUserByMail() {
        logger.info("Select user by id test");
        userMapper.insert(user);
        User selectedUser = userMapper.selectUserByMail("sjc@gmail.com");
        assertEquals("Equal objects", user, selectedUser);
    }*/




}
