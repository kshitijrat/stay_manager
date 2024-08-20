package com.practicespring.stay_manager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;



public interface MyRoomRepo extends JpaRepository<MyRoom, Integer>{
    MyRoom findById(int id);
    MyRoom findMyRoomByUser(User user);
    List<MyRoom> findByStatus(String status);
}
