package com.example.auth.controller;


import com.example.auth.entity.AppUser;
import com.example.auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/reg/user")
    public AppUser creatUser(@RequestBody AppUser appUser){
        appUser.setPass(passwordEncoder.encode(appUser.getPass()));
        return userRepo.save(appUser);
    }


}
