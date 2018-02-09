package org.sjimenez.chatapp.test;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
    public void insertUserControllerTest_Ok() throws JSONException {
        logger.info("Insert user controller test-ok");
        when(userMapper.insert(user)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("son iguales", user, response.getBody());
        assertEquals("Expected http response 200", HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void insertUserControllerTest_BadFormatOrNull() {
        logger.info("Insert user controller test-Bad format or null");
        user.setMail("it");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Expected http response 400", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test()
    public void insertUserControllerTest_DuplicatedKeyException() {
        logger.info("Insert user controller test-duplicated key exception");
        when(userMapper.insert(user)).thenThrow(org.springframework.dao.DuplicateKeyException.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Expected http response 409", HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test()
    public void insertUserControllerTest_PersistenceErrorException() {
        logger.info("Insert user controller test-persistence error");
        when(userMapper.insert(user)).thenThrow(org.apache.ibatis.exceptions.PersistenceException.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Expected http response 500", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteUserControllerTest_Ok() {
        logger.info("delete user controller test-ok");
        when(userMapper.selectUserById(1)).thenReturn(new User());
        when(userMapper.deleteUserById(1)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<Void>(headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/user/resources/delete/1", HttpMethod.DELETE, request, User.class);
        assertEquals("Status code must be 200", HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteUserControllerTest_NotFound() {
        logger.info("delete user controller test-not found");
        when(userMapper.selectUserById(1)).thenReturn(null);
        when(userMapper.deleteUserById(1)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<Void>(headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/user/resources/delete/1", HttpMethod.DELETE, request, User.class);
        assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateUserControllerTest_Ok() {
        logger.info("update user controller test-ok");
        user.setIduser(1);
        when(userMapper.selectUserById(1)).thenReturn(user);
        when(userMapper.updateUser(user)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/user/resources/updateUser", HttpMethod.PUT, request, User.class);
        assertEquals("Status code must be 200", HttpStatus.OK, response.getStatusCode());
        assertEquals("Same object", user, response.getBody());

    }

    @Test
    public void updateUserControllerTest_BadFormat() {
        logger.info("update user controller test-bad format");
        user.setIduser(1);
        user.setName(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/user/resources/updateUser", HttpMethod.PUT, request, User.class);
        assertEquals("Status code must be 400", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateUserControllerTest_NotFound() {
        logger.info("update user controller test-not found");
        user.setIduser(1);
        when(userMapper.selectUserById(1)).thenReturn(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + String.valueOf(port) + "/user/resources/updateUser", HttpMethod.PUT, request, User.class);
        assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void selectUserByIdControllerTest_Ok() {
        logger.info("select user by id controller test-ok");
        user.setIduser(1);
        when(userMapper.selectUserById(1)).thenReturn(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<Void>(headers);
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/getUserById/1", User.class);
        assertEquals("Status code must be 200", HttpStatus.OK, response.getStatusCode());
        assertEquals("Espected user", user, response.getBody());
    }

    @Test
    public void selectUserByIdControllerTest_NotFound() {
        logger.info("select user by id controller test-not found");
        user.setIduser(1);
        when(userMapper.selectUserById(1)).thenReturn(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<Void>(headers);
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/getUserById/1", User.class);
        assertEquals("Status code must be 404", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
