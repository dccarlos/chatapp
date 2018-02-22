package org.sjimenez.chatapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomeController {




    @RequestMapping(value = {"/", "/chat"})
    public String index() {


        System.out.println("/ chat");
        return "index";
    }

}