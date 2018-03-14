package org.sjimenez.chatapp.delegate;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.dao.GroupDao;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.Message;
import org.sjimenez.chatapp.test.UserDbMapperTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDelegateTest {
    @Autowired
    private MessageDelegate messageDelegate;

    @MockBean
    private GroupDao groupDao;

    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Before
    public void initUnitTest() {
    }

    @Test(expected = EntityNotFoundException.class)
    public void sendMessageToGroupTest() {
        when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
        messageDelegate.sendMessageToGroup("name1", new Message());
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchAllMessagesFromGroupTest() {
        when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
        messageDelegate.fetchAllMessagesFromGroup("name1");
    }

}
