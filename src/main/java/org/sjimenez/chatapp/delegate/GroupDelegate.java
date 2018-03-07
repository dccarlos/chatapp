package org.sjimenez.chatapp.delegate;

import java.util.List;

import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.User;

public class GroupDelegate {
	
	public Group createGroup(String groupName)
	{
		return null;
	}
	
	public Group fetchGroupByName(String groupName)
	{
		return null;
	}
	
	public Group updateGroupByName(String groupName, String newGroupName)
	{
		return null;
	}
	
	public void deleteGroupByName(String groupName)
	{
		//Remove users from the n:m table
		//Remove group from group table
	}
	
	public List<User> fetchUsersByGroupName(String groupName)
	{
		return null;
	}
	
	public List<User> addUserToGroup(String groupName, List<Integer> userIdList)
	{
		return null;
	}
	
	public List<User> removeUserFromGroup(String groupName, List<Integer> userIdList)
	{
		return null;
	}
}
