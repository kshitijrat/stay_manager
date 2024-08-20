package com.practicespring.stay_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.Feedback;
import com.practicespring.stay_manager.entities.User;


public interface FeedbackRepo extends JpaRepository<Feedback, Integer>{
    Feedback findByUser(User user);
}
