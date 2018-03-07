package org.sjimenez.chatapp.controllers;

import java.util.List;

import org.sjimenez.chatapp.delegate.MessageDelegate;
import org.sjimenez.chatapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/messageToGroup")
public class MessageToGroupController {

	private static final Logger log = LoggerFactory.getLogger(MessageToGroupController.class);

	@Autowired
	private MessageDelegate messageDelegate;

	@PostMapping("/{groupName}")
	public void sendMessageToGroup(@PathVariable("groupName") String groupName) {
		messageDelegate.sendMessageToGroup(groupName, null);
	}


	@GetMapping("/{groupName}")
	public ResponseEntity<List<Message>> fetchAllMessagesFromGroup(@PathVariable("groupName") String groupName) {
		return new ResponseEntity<List<Message>>(messageDelegate.fetchAllMessagesFromGroup(groupName), HttpStatus.OK);

	}
}
