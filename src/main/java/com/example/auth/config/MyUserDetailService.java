package com.example.auth.config;

import com.example.auth.entity.AppUser;
import com.example.auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user  = userRepo.findUsersByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
            // on doit convertire ce User a un UserDetails
            UserDetails appuser =  User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPass()) // ali
                    .roles(getRoles(userObj))
                    .build();

            return appuser;
        }else{
            throw new UsernameNotFoundException(username);
        }

    }

    private String[] getRoles(AppUser appUser){
        if(appUser.getRole() == null){
            return new String[]{"USER"};
        }
        return appUser.getRole().split(",");
    }
}
