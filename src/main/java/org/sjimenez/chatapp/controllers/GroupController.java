package org.sjimenez.chatapp.controllers;

import org.sjimenez.chatapp.delegate.GroupDelegate;
import org.sjimenez.chatapp.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is focused to create the CRUD actions for Groups that have been
 * consumed on any action related to the groups.
 * 
 * @author developer
 *
 */
@RestController
@RequestMapping(value = "/group")
public class GroupController {

	@Autowired
	private GroupDelegate groupDelegate;

	private static final Logger log = LoggerFactory.getLogger(GroupController.class);

	/**
	 * the creation Group method is responsible to create only the group without any
	 * user assigned to it. the group name should to be unique.
	 * 
	 * @param groupName
	 * @return
	 */
	@PostMapping("/{groupName}")
	public ResponseEntity<Group> createGroup(@PathVariable("groupName") String groupName) {
		return new ResponseEntity<Group>(groupDelegate.createGroup(groupName), HttpStatus.OK);

	}

	/**
	 * This method is responsible to fetch the Group information based on the group
	 * name, and get the users assigned to it.
	 */
	@GetMapping("/{groupName}")
	public ResponseEntity<Group> fetchGroupByName(@PathVariable("groupName") String groupName) {
		return new ResponseEntity<Group>(groupDelegate.fetchGroupByName(groupName), HttpStatus.OK);
	}

	/**
	 * This method is responsible only to change the group name based on the latest
	 * group name.
	 * 
	 * @param groupName
	 * @return
	 */
	@PutMapping("/{groupName}")
	public ResponseEntity<Group> updateGroupByName(@PathVariable("groupName") String groupName,
			@RequestParam("newGroupName") String newGroupName) {
		return new ResponseEntity<Group>(groupDelegate.updateGroupByName(groupName, newGroupName), HttpStatus.OK);
	}

	/**
	 * Delete the group identified with the group name.
	 */
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{groupName}")
	public ResponseEntity deleteGroupByName(@PathVariable("groupName") String groupName) {
		groupDelegate.deleteGroupByName(groupName);
		return new ResponseEntity(HttpStatus.OK);
	}
}
