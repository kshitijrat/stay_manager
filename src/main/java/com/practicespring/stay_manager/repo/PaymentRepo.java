package com.practicespring.stay_manager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.Payment;
import com.practicespring.stay_manager.entities.User;



public interface PaymentRepo extends JpaRepository<Payment,Integer>{

    // List<Payment> findByUser(int i);
    List<Payment> findByUser(User user);
    
}
