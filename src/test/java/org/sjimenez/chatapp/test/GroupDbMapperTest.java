package org.sjimenez.chatapp.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.mappers.GroupMapper;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:testdata.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GroupDbMapperTest {
    private Group groupToAssert;
    private User user1;
    private User user2;

    @Autowired
    GroupMapper groupMapper;
    @Autowired
    UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(GroupDbMapperTest.class);

    @Before
    public void initUnitTest() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ////prepare user1
        LocalDate date = LocalDate.parse("1999-05-05", formatter);

        user1 = new User();
        user1.setIduser(1);
        user1.setName("name1");
        user1.setLastName("last1");
        user1.setMail("ss1@ss1.com");
        user1.setNickname("nick1");
        user1.setBirthdate(date);
        ////prepare user2

        user2 = new User();
        user2.setIduser(2);
        user2.setName("name2");
        user2.setLastName("last2");
        user2.setMail("ss2@ss2.com");
        user2.setNickname("nick2");
        user2.setBirthdate(date);

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);

        ////prepare group
        groupToAssert = new Group();
        groupToAssert.setUserList(userList);
        groupToAssert.setName("groupname1");
        groupToAssert.setCreation(date);
        groupToAssert.setIdgroup(1);


    }
    @After
    public void after(){
        userMapper.truncateTableMessages();
        userMapper.truncateTableGroups();
        userMapper.truncateTableUsers();
        userMapper.truncateTableUsersGroup();
    }

    @Test
    public void selectGroupByIdTest() {

        List<Group> groupsList = groupMapper.selectAll();
        System.out.println(groupsList);

        List<UserGroupRelation> userGroupRelations = groupMapper.selectAllUserGroup();
        System.out.println(userGroupRelations.size());

        Group group = groupMapper.selectGroupById(1);
        Assert.assertEquals("diferent object get group with list", groupToAssert, group);
        System.out.println(group);
    }

    @Test
    public void selectGroupByNameTest() {
        Group group = groupMapper.selectGroupByName("groupname1");
        Assert.assertEquals("diferent object get group with list", groupToAssert, group);
    }
    @Test
    public void selectUsersByGroupId(){
        List<User> userList=groupMapper.selectUsersById(1);
        Assert.assertNotNull(userList);
        Assert.assertNotEquals("Must Not be empty",userList.size(),0);
    }

    @Test
    public void insertGroupTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("1999-05-05", formatter);
        Group groupToAssert = new Group();
        groupToAssert.setName("insertTest");
        groupToAssert.setCreation(date);
        groupMapper.insertGroup(groupToAssert);
        Group group = groupMapper.selectGroupById(4);
        Assert.assertNotNull("Null Object", group);
    }

    @Test
    public void updateGroupTest() {
        groupMapper.updateGroup("groupname3", "group3");
        Group group = groupMapper.selectGroupById(3);
        Assert.assertNotNull("Null Object", group);
    }

    @Test
    @Ignore
    public void deleteGroupTest() {

        int sizeBefore = groupMapper.selectAll().size();
        int relationSizeBefore = groupMapper.selectAllUserGroup().size();
        groupMapper.deleteGroupById(1);
        int sizeAfter = groupMapper.selectAll().size();
        int relationSizeAfter = groupMapper.selectAllUserGroup().size();
        Assert.assertNotEquals("Must not be equals", sizeBefore, sizeAfter);
        Assert.assertNotEquals("Must not be equals", relationSizeAfter, relationSizeBefore);
    }

    @Test
    public void selectUserGroupRelationTest() {
        UserGroupRelation userGroupRelation = groupMapper.selectUserGroupRelation(1, 2);
        Assert.assertNotNull("Null", userGroupRelation);
        Assert.assertEquals("Not Equals", userGroupRelation.getIduser_group(), 1);
    }

    @Test
    public void insertUserGroupRelation() {
        int relationSizeBefore = groupMapper.selectAllUserGroup().size();
        groupMapper.insertUserGroupRelation(1, 3);
        int relationSizeAfter = groupMapper.selectAllUserGroup().size();
        Assert.assertEquals("Must not be equals", relationSizeBefore + 1, relationSizeAfter);
    }

    @Test
    public void deleteUserFromGroup() {
        groupMapper.deleteUserFromGroup(3, 3);
        UserGroupRelation userGroupRelation = groupMapper.selectUserGroupRelation(3, 3);
        Assert.assertNull("Is not null then the object have not been deleted", userGroupRelation);
    }

    @Test
    public void GetMessageFromGroup() {
        List<Message> messageList = groupMapper.selectMessagesFromGroup(1);
        Assert.assertNotNull("Null",messageList);
    }
}
