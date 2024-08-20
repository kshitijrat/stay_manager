package com.practicespring.stay_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.Activite;
import com.practicespring.stay_manager.entities.User;



public interface ActiveRepo extends JpaRepository<Activite, Integer>{

    Activite findByUser(User user);
    
} 
