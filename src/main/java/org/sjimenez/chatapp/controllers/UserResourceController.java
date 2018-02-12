package org.sjimenez.chatapp.controllers;

import org.sjimenez.chatapp.dao.ChatDao;
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
import java.util.List;

@RestController
@RequestMapping(value = "/user/resources")
public class UserResourceController {
    private static final Logger logger = LoggerFactory.getLogger(UserResourceController.class);

    @Autowired
    private ChatDao chatDao;
    private UserMapper userMapper;

    @GetMapping("/all")
    public List<User> getAll() {
        return chatDao.findAll();
    }

    @PostMapping("/insert")
    public ResponseEntity<User> insert(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("ocurred an error while validating request data");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        try {
            chatDao.insertUser(user);
        } catch (org.springframework.dao.DataAccessException ex) {
            if (ex.contains(org.springframework.dao.DuplicateKeyException.class)) {
                logger.error("Duplicated keys error" + ex);
                return new ResponseEntity<User>(HttpStatus.CONFLICT);
            }
            logger.error("Error when inserting in database" + ex);
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/getUserById/{iduser}")
    public ResponseEntity<User> getUserById(@PathVariable("iduser") Integer iduser) {
        User user = chatDao.selectUserById(iduser);
        if (user == null) {
            logger.info("user with id to retrieve not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    private ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("ocurred an error while validating request data");
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User currentUser = chatDao.selectUserById(user.getIduser());
        if (currentUser == null) {
            logger.info("user to update not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        chatDao.updateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{iduser}")
    private ResponseEntity<Void> delete(@PathVariable("iduser") Integer iduser) {
        User currentUser = chatDao.selectUserById(iduser);
        if (currentUser == null) {
            logger.info("user to delete not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        chatDao.deleteUserById(iduser);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
