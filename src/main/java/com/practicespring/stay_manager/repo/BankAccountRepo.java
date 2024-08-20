package com.practicespring.stay_manager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.BankAccount;
import com.practicespring.stay_manager.entities.User;



public interface BankAccountRepo extends JpaRepository<BankAccount, Integer>{
    List<BankAccount> findByUser(User user);
    BankAccount findByCardNumber(String cardNumber);
}
