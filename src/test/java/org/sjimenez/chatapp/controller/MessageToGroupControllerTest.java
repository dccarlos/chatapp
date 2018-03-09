package org.sjimenez.chatapp.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sjimenez.chatapp.controllers.MessageToGroupController;
import org.sjimenez.chatapp.delegate.MessageDelegate;
import org.sjimenez.chatapp.model.Message;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MessageToGroupControllerTest {

	@Mock
	private MessageDelegate messageDelegate;
	
	@InjectMocks
	private MessageToGroupController messageToGroupController;
	
	private String groupName = "testGroup1";
	
	private Message message;
	
	@Before
	public void init() {
		message = new Message();
		message.setContentMessage("New Message");
	}
	
	@Test
	public void sendMessageToGroup()
	{
		messageToGroupController.sendMessageToGroup(groupName, message);
		
		verify(messageDelegate, times(1)).sendMessageToGroup(groupName, message);
	}
	
	@Test
	public void fetchAllMessagesFromGroup()
	{
		List<Message> messageList = new ArrayList<Message>();
		messageList.add(message);
		messageList.add(message);
		messageList.add(message);
		
		when(messageDelegate.fetchAllMessagesFromGroup(groupName)).thenReturn(messageList);
		ResponseEntity<List<Message>> responseEntity =  messageToGroupController.fetchAllMessagesFromGroup(groupName);
		
		verify(messageDelegate).fetchAllMessagesFromGroup(groupName);
		assertArrayEquals(messageList.toArray(), responseEntity.getBody().toArray());
	}
}
