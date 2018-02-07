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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDbMapperTest {


    @Autowired
    private UserMapper userMapper;

    static private User user;

    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Before
    public void truncateTable() {
        logger.warn("BEFORE TESTT");
       userMapper.truncateTableUsers();
    }
    @BeforeClass
    static public void initTestObjects() throws ParseException {
        logger.warn("Before Class");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        LocalDate date = LocalDate.parse("2005-nov-12", formatter);

        //Date date=new Date();
        System.out.println(date.toString());
        user=new User();
        user.setName("roshi");
        user.setLastName("kame");
        user.setMail("sjc@gmail.com");
        user.setNickname("jackiechun");
        user.setBirthdate(date);
    }

    @Test
    public void insertUser() {
        logger.warn("INSERT TEST");
        userMapper.insert(user);
        List<User> list=userMapper.findAll();
        assertEquals("Compare returned id",1,user.getIduser());
        assertEquals("total rows",1,list.size());
        System.out.println("fefefecha:"+list.get(0).getBirthdate());
    }








}
