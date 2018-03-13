package org.sjimenez.chatapp.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:testdata.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GroupToUserDdMapperTest {


    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectGroupsFromUserById() {
        logger.info("groups from user");
        List<Group> groupList=userMapper.selectGroupsFromUserById(1);
        System.out.println(groupList);
        Assert.assertNotEquals("Must not be cero",groupList.size(),0);
    }

}
