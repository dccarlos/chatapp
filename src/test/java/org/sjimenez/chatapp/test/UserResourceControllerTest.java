package org.sjimenez.chatapp.test;

import org.json.JSONException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceControllerTest {
    static private User user;

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private UserMapper userMapper;
    @LocalServerPort
    private int port;
    private static final Logger logger = LoggerFactory.getLogger(UserDbMapperTest.class);

    @Before
    public void initUnitTest() {
        logger.info("Before");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2005-06-12", formatter);
        user = new User();
        user.setName("roshi");
        user.setLastName("kame");
        user.setMail("sjc@gmail.com");
        user.setNickname("jackiechun");
        user.setBirthdate(date);
    }

    @Test
    public void insertControllerTest() throws JSONException {
        when(userMapper.insert(user)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("son iguales", user, response.getBody());
    }

    @Test
    public void insertControllerTest_BadFormatOrNull() {
        user.setMail("it");
        when(userMapper.insert(user)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        Assert.assertNull(response.getBody());
    }

    @Test()
    public void insertControllerTest_DuplicatedKeyException() {
        when(userMapper.insert(user)).thenThrow(org.springframework.dao.DuplicateKeyException.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Equal errors", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test()
    public void insertControllerTest_PersistenceErrorException() {
        when(userMapper.insert(user)).thenThrow(org.apache.ibatis.exceptions.PersistenceException.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Equal errors", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
