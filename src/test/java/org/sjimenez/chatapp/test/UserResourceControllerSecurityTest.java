package org.sjimenez.chatapp.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.sjimenez.chatapp.dao.ChatDao;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "classpath:testdata.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserResourceControllerSecurityTest {
    static private User user;

    @Autowired
    private PasswordEncoder PASSWORD_ENCODER;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ChatDao chatDao;
    @MockBean
    private User mockUser;
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
    @After
    public void after(){
        userMapper.truncateTableMessages();
        userMapper.truncateTableGroups();
        userMapper.truncateTableUsers();
        userMapper.truncateTableUsersGroup();
    }

    @Test()
    public void insertUserControllerTest_Encode() {
        logger.info("Insert user controller test-Password encoding");
        when(chatDao.insertUser(user)).thenReturn(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + String.valueOf(port) + "/user/resources/insert", request, User.class);
        assertEquals("Expected http response 200", HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(PASSWORD_ENCODER.matches("jackiechun", response.getBody().getNickname()));
    }
}
