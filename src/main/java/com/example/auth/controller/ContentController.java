package com.example.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ContentController {

    @GetMapping("/home")
    public String handelWelcome(){
        return "home";
    }
    @GetMapping("/admin/home")
    public String handelAdmineWelcome(){
        return "home_admin";
    }
    @GetMapping("/user/home")
    public String handelUserWelcome(){
        return "home_user";
    }

    @GetMapping("/login")
    public String handelLogin(){
        return "custome_login";
    }




}
