package org.sjimenez.chatapp.controllers;

import org.sjimenez.chatapp.pojo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMesage(String message,Principal principal){
        //this.template.convertAndSend("/chat",  new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);
        this.template.convertAndSend("/chat",new ChatMessage(principal.getName(),message));
    }
}
