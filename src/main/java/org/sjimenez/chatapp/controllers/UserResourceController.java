package org.sjimenez.chatapp.controllers;

import org.sjimenez.chatapp.mappers.UserMapper;
import org.sjimenez.chatapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User insert(@Valid @RequestBody User user, BindingResult bindingResult)throws org.springframework.dao.DuplicateKeyException,org.apache.ibatis.exceptions.PersistenceException  {
        if (bindingResult.hasErrors()) {
            logger.warn("ocurred an error while validating");
            return null;
        }
        try {
            userMapper.insert(user);
        } catch (org.apache.ibatis.exceptions.PersistenceException ex) {
            logger.error("Error when inserting in database" + ex);
            throw ex;
        } catch (org.springframework.dao.DuplicateKeyException ex) {
            logger.error("Duplicated keys error" + ex);
            throw ex;
        }
        return user;
    }
}
