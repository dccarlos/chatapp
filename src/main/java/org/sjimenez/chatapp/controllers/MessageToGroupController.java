package org.sjimenez.chatapp.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.sjimenez.chatapp.delegate.MessageDelegate;
import org.sjimenez.chatapp.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class responsible to handle messages related with the group, exposing the
 * functionality through a rest end point
 * 
 * @author developer
 *
 */
@RestController
@RequestMapping(value = "/messageToGroup")
@Validated
public class MessageToGroupController {

	@Autowired
	private MessageDelegate messageDelegate;

	/**
	 * This method is responsible to publish one single message to a specific group
	 * identified by its group name
	 * 
	 * @param groupName
	 */
	@PostMapping("/{groupName}")
	public void sendMessageToGroup(@PathVariable("groupName") @NotEmpty @NotNull String groupName,
			@Valid @RequestBody Message message) {
		messageDelegate.sendMessageToGroup(groupName, message);
	}

	/**
	 * It is responsible to fetch a list of messages on the database related to a
	 * specific group identified by its groupName
	 * 
	 * @param groupName
	 * @return
	 */
	@GetMapping("/{groupName}")
	public ResponseEntity<List<Message>> fetchAllMessagesFromGroup(
			@PathVariable("groupName") @NotEmpty @NotNull String groupName) {
		return new ResponseEntity<List<Message>>(messageDelegate.fetchAllMessagesFromGroup(groupName), HttpStatus.OK);

	}
}
