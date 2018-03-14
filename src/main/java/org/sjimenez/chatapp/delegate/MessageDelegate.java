package org.sjimenez.chatapp.delegate;

import java.util.List;
import java.util.Optional;

import org.sjimenez.chatapp.dao.GroupDao;
import org.sjimenez.chatapp.dao.MessageDao;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

public class MessageDelegate {
    private static final Logger logger = LoggerFactory.getLogger(MessageDelegate.class);
    @Autowired
    GroupDao groupDao;
    @Autowired
    MessageDao messageDao;


    public void sendMessageToGroup(String groupName, Message messageContent) {
        Optional<Group> group = groupDao.selectGroupByName(groupName);
        if (!group.isPresent()) {
            logger.warn("Group not found");
            throw new EntityNotFoundException();
        }
        int idgroup = group.get().getIdgroup();
        messageContent.setIdgroup(idgroup);
        messageDao.insertMessage(messageContent);
    }

    public List<Message> fetchAllMessagesFromGroup(String groupName) {
        Optional<Group> group = groupDao.selectGroupByName(groupName);
        if (!group.isPresent()) {
            logger.warn("Group not found");
            throw new EntityNotFoundException();
        }
        int idgroup = group.get().getIdgroup();
        return groupDao.selectMessagesFromGroup(idgroup);
    }

}
