package com.practicespring.stay_manager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByUserEmail(String email);
    List<User> findUserByRoles(String roles);
}

