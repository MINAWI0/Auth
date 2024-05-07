package com.example.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecConfig {

    @Autowired
    private MyUserDetailService myUserDetailService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                registry ->{
                registry.requestMatchers("/home" , "/reg/**").permitAll();
                registry.requestMatchers("/admin/**").hasRole("ADMIN");
                registry.requestMatchers("/user/**").hasAnyRole("USER","ADMIN");

                registry.anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .successHandler(new AuthSucessHandler() )
                            .loginPage("/login").permitAll();
                })
                .build();
    }


//    @Bean
//    public  UserDetailsService userDetailsService(){
//        UserDetails normalUser = User.builder()
//                .username("minaoui")
//                .password("$2a$12$NO9LPnRz2UGE21lCk7STZeUCtmSF7ti8HoHnFq21c5bCXrrODlB/a") // minaoui
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User.builder()
//                .username("ali")
//                .password("$2a$12$n4Q6rCQr2lyRd1nufPGCyOuRpU7yvPzz5YF2qawhrVVIY94iXdc6S") // ali
//                .roles("ADMIN","USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser , adminUser);
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        return myUserDetailService;
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }






    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
