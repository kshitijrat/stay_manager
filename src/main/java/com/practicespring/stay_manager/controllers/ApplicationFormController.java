package com.practicespring.stay_manager.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;
import com.practicespring.stay_manager.repo.UserRepo;


@Controller
public class ApplicationFormController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

 

    @GetMapping("/applicationform")
    public String showApplicationFrom(Model model) {
        // String email = authentication.getName();
        // User user = userRepo.findUserByUserEmail(email);
        // model.addAttribute("user", user);
        return "applicationform";
    }

    @PostMapping("/register2")
    public String submitApplicationForm(@RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("roomNumber") String roomNumber,
            @RequestParam("buildingName") String buildingName,
            @RequestParam("floor") String floor,
            @RequestParam("bedNumber") String bedNumber,
            @RequestParam("hostelNumber") int hostelNumber,
            @RequestParam("gender") String gender,
            @RequestParam("roomType") String roomType,
            Model model) {
        System.out.println("Runnnnnn Application Form(/register2)***************");
        User user = userRepo.findUserByUserEmail(email);
        if (user == null) {
            System.out.println("User is null");
            System.out.println("User: " + user);
            return "redirect:/applicationform";
        }
        user.setRoomNumber(roomNumber);
        user.setRoomType(roomType);
        user.setGender(gender);
        user.setHostelNumber(hostelNumber);
        user.setUserName(name);
        user.setUserPhone(phone);
        userRepo.save(user);

        MyRoom myRoom = new MyRoom();
        myRoom.setBedNumber(bedNumber);
        myRoom.setBuildingName(buildingName);
        myRoom.setFloor(floor);
        myRoom.setRoomNumber(roomNumber);
        myRoom.setUser(user);
        myRoom.setRoomType(roomType);
        myRoom.setStatus("Pending");
        myRoom.setDate(getCurrentDate()+"  "+getCurrentTime());
        myRoomRepo.save(myRoom);

        return "redirect:/dashboard";
    }

    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    public String getCurrentTime(){
        LocalTime currTime = LocalTime.now();
        int hr = currTime.getHour();
        int min  = currTime.getMinute();
        int sec = currTime.getSecond();
        return hr+":"+min+":"+sec+"";
    }

}
