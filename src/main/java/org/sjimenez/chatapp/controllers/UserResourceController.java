package org.sjimenez.chatapp.controllers;

import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "/user/resources")
public class UserResourceController {
    private static final Logger logger = LoggerFactory.getLogger(UserResourceController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all")
    public List<User> getAll() {
        return userMapper.findAll();
    }

    @PostMapping("/insert")
    public ResponseEntity<User> insert(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("ocurred an error while validating");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        try {
            userMapper.insert(user);
        } catch (org.apache.ibatis.exceptions.PersistenceException ex) {
            logger.error("Error when inserting in database" + ex);
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (org.springframework.dao.DuplicateKeyException ex) {
            logger.error("Duplicated keys error" + ex);
            return new ResponseEntity<User>(HttpStatus.CONFLICT);

        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
