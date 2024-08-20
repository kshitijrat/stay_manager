package com.practicespring.stay_manager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;



@Service
public class MyRoomService {
    
    @Autowired
    private MyRoomRepo myRoomRepo;

   

     public List<MyRoom> getAllRooms() {
        return myRoomRepo.findAll();
    }

    public MyRoom getRoomById(int id) {
        return myRoomRepo.findById(id);
    }

    public MyRoom saveRoom(MyRoom room) {
        return myRoomRepo.save(room);
    }

    public void deleteRoom(int id) {
        myRoomRepo.deleteById(id);
    }

    public List<User> getPendingRequestUser(){
        List<MyRoom> myRoomsList = myRoomRepo.findAll();
        List<User> userList = new ArrayList<>();
        for(MyRoom mr : myRoomsList){
            if(mr.getStatus().equals("Pending")){
                User user = mr.getUser();
                userList.add(user);
            }
        }
        return userList;
    }

    public boolean isValidRoom(MyRoom myRoom){
        String roomNumber = myRoom.getRoomNumber();
        String bedNumber = myRoom.getBedNumber();
        List<MyRoom> list = myRoomRepo.findAll();
        for(MyRoom mr : list){
            if(mr.getId() == myRoom.getId())continue;
            if(mr.getRoomNumber().equals(roomNumber) || mr.getBedNumber().equals(bedNumber))return false;
        }
        return true;
    }

}
