package org.sjimenez.chatapp.controllers;


import org.sjimenez.chatapp.mappers.GroupMapper;
import org.sjimenez.chatapp.mappers.MessageMapper;
import org.sjimenez.chatapp.model.Group;
import org.sjimenez.chatapp.model.Message;
import org.sjimenez.chatapp.model.User;
import org.sjimenez.chatapp.model.UserGroupRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/group/resources")
public class GroupResourceController {

    @Autowired
    GroupMapper groupMapper;
    @Autowired
    MessageMapper messageMapper;



    @GetMapping("/getGroupById/{iduser}")
    public ResponseEntity<Group> getGroupById(@PathVariable("iduser") Integer iduser) {

        System.out.println("iduser"+iduser);
        Group group = groupMapper.selectGroupById(iduser);
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }
    @GetMapping("/getUserById/{iduser}")
    public ResponseEntity<User> getUserById(@PathVariable("iduser") Integer iduser) {
        System.out.println("iduser"+iduser);
        return new ResponseEntity<User>(HttpStatus.OK);
    }
    @GetMapping("/getGroupByName")
    public ResponseEntity<Group> getGroupByName() {

        System.out.println("iduser"+"uno");
        Group group = groupMapper.selectGroupByName("uno");
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }

    @GetMapping("/insertGroup")
    public ResponseEntity<Void> insertGroup() {

        String str = "2015-03-15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        Group group=new Group();
        group.setCreation(dateTime);
        group.setName("jiren");
        System.out.println("iduser"+"uno");
        groupMapper.insertGroup(group);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/updateGroup")
    public ResponseEntity<Void> updateGroup() {
        groupMapper.updateGroup("migatte","jiren");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/deleteGroup")
    public ResponseEntity<Void> deleteGroup() {
        groupMapper.deleteGroupById(4);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/getUserGroupRelation")
    public ResponseEntity<UserGroupRelation> getUserGroupRelation() {
        System.out.println("getusergroup");
        UserGroupRelation userGroupRelation=groupMapper.selectUserGroupRelation(1,4);
        groupMapper.insertUserGroupRelation(3,8);
        return new ResponseEntity<UserGroupRelation>(userGroupRelation,HttpStatus.OK);
    }

    @GetMapping("/deleteUserFromGroup")
    public ResponseEntity<Void> deleteUserFromGroup() {
        groupMapper.deleteUserFromGroup(3,7);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message>listOfMessages=groupMapper.selectMessagesFromGroup(1);
        return new ResponseEntity<List<Message>>(listOfMessages,HttpStatus.OK);
    }

    @GetMapping("/insertMessage")
    public ResponseEntity<Void> insertMessage() {
        String str = "2015-03-15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        Message message=new Message();
        message.setContent("hola primer mensaje puesto desde mapper");
        message.setCreation(dateTime);
        message.setIdgroup(1);
        messageMapper.insertMessage(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }





}
