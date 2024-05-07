package com.example.auth.repo;

import com.example.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser,Long> {
        public Optional<AppUser> findUsersByUsername(String username);
}
