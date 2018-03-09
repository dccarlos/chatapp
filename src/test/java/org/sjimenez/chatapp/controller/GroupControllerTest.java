package org.sjimenez.chatapp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sjimenez.chatapp.controllers.GroupController;
import org.sjimenez.chatapp.delegate.GroupDelegate;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GroupControllerTest {

	@Mock
	private GroupDelegate groupDelegate;

	@InjectMocks
	private GroupController groupController;

	private Group testGroupBean;
	
	@Before
	public void init() {
		testGroupBean = createGroupForTest("GroupTesting1");
	}

	@Test
	public void createGroup() {
		when(groupDelegate.createGroup(testGroupBean.getName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.createGroup(testGroupBean.getName());

		verify(groupDelegate).createGroup(testGroupBean.getName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getName(), is(testGroupBean.getName( )) );
		assertThat( responseEntity.getBody().getIdgroup(), is(testGroupBean.getIdgroup()) );
		assertThat( responseEntity.getBody().getCreation(), is(testGroupBean.getCreation()) );
	}

	@Test
	public void fetchGroupByName() {
		when(groupDelegate.fetchGroupByName(testGroupBean.getName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.fetchGroupByName(testGroupBean.getName());

		verify(groupDelegate).fetchGroupByName(testGroupBean.getName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getName(), is(testGroupBean.getName( )) );
		assertThat( responseEntity.getBody().getIdgroup(), is(testGroupBean.getIdgroup()) );
		assertThat( responseEntity.getBody().getCreation(), is(testGroupBean.getCreation()) );
	}

	
	public void updateGroupByName() {
		when(groupDelegate.fetchGroupByName(testGroupBean.getName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.fetchGroupByName(testGroupBean.getName());

		verify(groupDelegate).fetchGroupByName(testGroupBean.getName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getName(), is(testGroupBean.getName( )) );
		assertThat( responseEntity.getBody().getIdgroup(), is(testGroupBean.getIdgroup()) );
		assertThat( responseEntity.getBody().getCreation(), is(testGroupBean.getCreation()) );
	}

	@Test
	public void deleteGroupByName() {
		
		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = groupController.deleteGroupByName(testGroupBean.getName() );
		
		verify(groupDelegate, times(1)).deleteGroupByName(testGroupBean.getName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
	}

	private Group createGroupForTest(String groupName) {
		Group group = new Group();
		group.setName(groupName);
		group.setCreation(LocalDate.now());
		group.setIdgroup(1);

		return group;
	}
}
