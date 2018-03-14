package org.sjimenez.chatapp.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sjimenez.chatapp.controllers.UserToGroupController;
import org.sjimenez.chatapp.dao.GroupDao;
import org.sjimenez.chatapp.delegate.GroupDelegate;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.User;
import org.sjimenez.chatapp.test.UserDbMapperTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserToGroupControllerTest {
	@Mock
	private GroupDelegate groupDelegate;

	@InjectMocks
	private UserToGroupController userToGroupController;
	
	private Group testGroupBean;
	
	private List<User> userList;
	
	private User user;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private GroupDao groupDao;

	@LocalServerPort
	private int port;
	private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);
	
	@Before
	public void init() {
		testGroupBean = createGroupForTest("GroupTesting1");
		
		userList = new ArrayList<User>();
		
		userList.add(createUserForTest());
		userList.add(createUserForTest());
		userList.add(createUserForTest());		
	}
	
	@Test
	public void fetchUsersByGroupName()
	{
		when(groupDelegate.fetchUsersByGroupName(testGroupBean.getName())).thenReturn(userList);
		ResponseEntity<List<User>> responseEntity =  userToGroupController.fetchUsersByGroupName(testGroupBean.getName());
		
		verify(groupDelegate, times(1)).fetchUsersByGroupName(testGroupBean.getName());
		assertArrayEquals(userList.toArray(), responseEntity.getBody().toArray());
	}
	
	@Test
	public void addUserToGroup()
	{
		List<Integer> newUsers = new ArrayList<Integer>();
		user = createUserForTest();
		newUsers.add(user.getIduser() );
		userList.add(user);
		
		when(groupDelegate.addUserToGroup(testGroupBean.getName(), newUsers)).thenReturn(userList);
		ResponseEntity<List<User>> responseEntity =  userToGroupController.addUserToGroup(testGroupBean.getName(), newUsers);
		
		verify(groupDelegate).addUserToGroup(testGroupBean.getName(), newUsers);
		assertArrayEquals(userList.toArray(), responseEntity.getBody().toArray());
	}
	
	@Test
	public void removerUserFromGroup()
	{
		List<Integer> removeUsers = new ArrayList<Integer>();
		user = userList.get(userList.size()-1 );
		removeUsers.add(user.getIduser() );
		userList.remove(user);
		
		when(groupDelegate.removeUserFromGroup(testGroupBean.getName(), removeUsers)).thenReturn(userList);
		ResponseEntity<List<User>> responseEntity =  userToGroupController.removeUserFromGroup(testGroupBean.getName(), removeUsers);
		
		verify(groupDelegate).removeUserFromGroup(testGroupBean.getName(), removeUsers);
		assertArrayEquals(userList.toArray(), responseEntity.getBody().toArray());
	}

	@Test
	public void deleteGroupByName_EmptyList(){
		when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
		//groupDelegate.removeUserFromGroup("name1", new ArrayList<Integer>(){{ add(1);add(2);add(3); }});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<Void>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/userToGroup/name1?userList=", HttpMethod.DELETE, request, Void.class);
		assertEquals("List is not empty", HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void deleteGroupByName_NotFoundException(){
		when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
		//groupDelegate.removeUserFromGroup("name1", new ArrayList<Integer>(){{ add(1);add(2);add(3); }});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<Void>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/userToGroup/name1?userList=1,2", HttpMethod.DELETE, request, Void.class);
		assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void addUserToGroup_EmptyList(){
		when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
		//groupDelegate.removeUserFromGroup("name1", new ArrayList<Integer>(){{ add(1);add(2);add(3); }});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<Void>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/userToGroup/name1?userList=", HttpMethod.POST, request, Void.class);
		assertEquals("List is not empty", HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void addUserToGroup_NotFoundException(){
		when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
		//groupDelegate.removeUserFromGroup("name1", new ArrayList<Integer>(){{ add(1);add(2);add(3); }});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<Void>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/userToGroup/name1?userList=1,2", HttpMethod.POST, request, Void.class);
		assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void fetchUsersByGroupName_NotFoundException(){
		when(groupDao.selectGroupByName("name1")).thenReturn(Optional.ofNullable(null));
		//groupDelegate.removeUserFromGroup("name1", new ArrayList<Integer>(){{ add(1);add(2);add(3); }});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity<Void>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/userToGroup/name1?userList", HttpMethod.GET, request, Void.class);
		assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	private Group createGroupForTest(String groupName) {
		Group group = new Group();
		group.setName(groupName);
		group.setCreation(LocalDate.now());
		group.setIdgroup(1);

		return group;
	}
	
	private User createUserForTest()
	{
		LocalTime localtime = LocalTime.now();
		int randomNum = ThreadLocalRandom.current().nextInt(1, 15000 + 1);
			
		User user = new User();
		user.setBirthdate(LocalDate.now());
		user.setIduser(randomNum);
		user.setLastName("lastName");
		user.setMail(localtime.toString() + "@hotmail.com");
		user.setName("name");
		user.setNickname(localtime.toString());
		
		return user;
	}
}
