package com.practicespring.stay_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;
import com.practicespring.stay_manager.repo.UserRepo;
import com.practicespring.stay_manager.services.MyRoomService;



@Controller
public class PendingApproveRequest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private MyRoomService myRoomService;

    @PostMapping("/admin/approverequest")
    public String approveRequest(@RequestParam("email") String email) {
        try {
            System.out.println("Pending request controller run....................");
            User user = userRepo.findUserByUserEmail(email);
            MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);

            if(!myRoomService.isValidRoom(myRoom)){
                myRoom.setStatus("This room is not available");
                myRoomRepo.save(myRoom);
                return "redirect:/requestformadmin";
            }

            System.out.println("\n" + myRoom.getStatus() + "\n");
            System.out.println("roomid: " + myRoom.getId());
            myRoom.setStatus("Approved");
            myRoomRepo.save(myRoom);
            System.out.println(myRoom.getStatus());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return "redirect:/requestformadmin";

    }

}
