package org.sjimenez.chatapp.test;


import org.junit.*;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.mappers.GroupMapper;
import org.sjimenez.chatapp.mappers.MessageMapper;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.Message;
import org.sjimenez.chatapp.model.User;
import org.sjimenez.chatapp.model.UserGroupRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:testdata.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MessageDbMapperTest {

    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(MessageDbMapperTest.class);

    @Before
    public void initUnitTest() {
    }
    @After
    public void after(){
        userMapper.truncateTableMessages();
        userMapper.truncateTableGroups();
        userMapper.truncateTableUsers();
        userMapper.truncateTableUsersGroup();
    }

    @Test
    public void selectAllMessagesTest(){
        List<Message> messageList=messageMapper.selectAll();
        Assert.assertNotNull("Is null",messageList);
        System.out.println(messageList.size());
    }
    @Test
    public void insertMessageTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("1999-05-05", formatter);
        int sizeBefore=messageMapper.selectAll().size();
        Message newMessage=new Message();
        newMessage.setIdgroup(1);
        newMessage.setCreation(date);
        newMessage.setContent("HelloH2Database");
        messageMapper.insertMessage(newMessage);
        int sizeAfter=messageMapper.selectAll().size();
        Assert.assertEquals("Diferent Size",sizeBefore+1,sizeAfter);
    }






}
