package org.sjimenez.chatapp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sjimenez.chatapp.controllers.GroupController;
import org.sjimenez.chatapp.delegate.GroupDelegate;
import org.sjimenez.chatapp.model.Group;
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
		when(groupDelegate.createGroup(testGroupBean.getGroupName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.createGroup(testGroupBean.getGroupName());

		verify(groupDelegate).createGroup(testGroupBean.getGroupName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getGroupName(), is(testGroupBean.getGroupName( )) );
		assertThat( responseEntity.getBody().getGroupId(), is(testGroupBean.getGroupId()) );
		assertThat( responseEntity.getBody().getCreatedDate(), is(testGroupBean.getCreatedDate()) );
	}

	@Test
	public void fetchGroupByName() {
		when(groupDelegate.fetchGroupByName(testGroupBean.getGroupName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.fetchGroupByName(testGroupBean.getGroupName());

		verify(groupDelegate).fetchGroupByName(testGroupBean.getGroupName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getGroupName(), is(testGroupBean.getGroupName( )) );
		assertThat( responseEntity.getBody().getGroupId(), is(testGroupBean.getGroupId()) );
		assertThat( responseEntity.getBody().getCreatedDate(), is(testGroupBean.getCreatedDate()) );
	}

	
	public void updateGroupByName() {
		when(groupDelegate.fetchGroupByName(testGroupBean.getGroupName())).thenReturn(testGroupBean);
		ResponseEntity<Group> responseEntity = groupController.fetchGroupByName(testGroupBean.getGroupName());

		verify(groupDelegate).fetchGroupByName(testGroupBean.getGroupName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
		assertThat( responseEntity.getBody().getGroupName(), is(testGroupBean.getGroupName( )) );
		assertThat( responseEntity.getBody().getGroupId(), is(testGroupBean.getGroupId()) );
		assertThat( responseEntity.getBody().getCreatedDate(), is(testGroupBean.getCreatedDate()) );
	}

	@Test
	public void deleteGroupByName() {
		
		@SuppressWarnings("rawtypes")
		ResponseEntity responseEntity = groupController.deleteGroupByName(testGroupBean.getGroupName() );
		
		verify(groupDelegate, times(1)).deleteGroupByName(testGroupBean.getGroupName());
		assertThat( responseEntity.getStatusCode(), is(HttpStatus.OK) );
	}

	private Group createGroupForTest(String groupName) {
		Group group = new Group();
		group.setGroupName(groupName);
		group.setCreatedDate(LocalDate.now());
		group.setGroupId(1);

		return group;
	}
}
