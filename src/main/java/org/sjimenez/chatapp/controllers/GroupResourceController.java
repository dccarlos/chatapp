package org.sjimenez.chatapp.controllers;


import org.sjimenez.chatapp.mappers.GroupMapper;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value ="/group/resources")
public class GroupResourceController {

    @Autowired
    GroupMapper groupMapper;



    @GetMapping("/getGroupById/{iduser}")
    public ResponseEntity<Group> getGroupById(@PathVariable("iduser") Integer iduser) {

        System.out.println("iduser"+iduser);
        Group group = groupMapper.selectGroupById(iduser);
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }
    @GetMapping("/getUserById/{iduser}")
    public ResponseEntity<User> getUserById(@PathVariable("iduser") Integer iduser) {

        System.out.println("iduser"+iduser);
        User user = groupMapper.selectUserByIdd(iduser);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }






}
