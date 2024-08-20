package com.practicespring.stay_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.UserRepo;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    

    public User findByUsername(String email) {
        return userRepository.findUserByUserEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByUserEmail(email);
    }

  

}
