package com.practicespring.stay_manager.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;
import com.practicespring.stay_manager.repo.UserRepo;
import com.practicespring.stay_manager.services.MyRoomService;



@Controller
public class MyRoomController {

    @Autowired
    private MyRoomService myRoomService;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/myroom")
    public String myRoom(Model model, Authentication authentication) {
        // Fetch the user's room data (assuming there's a method to get user's room
        // info)
        String email = authentication.getName();
        User user = userRepo.findUserByUserEmail(email);
        MyRoom room = myRoomRepo.findMyRoomByUser(user); // Replace 1L with actual user-specific room ID

        System.out.println("\n room: " + room);
        System.out.println("myroom controller*******************************************");

        // delete all un allocated room
        if (room != null && room.getStatus().equals("This room is not available")) {
            myRoomService.deleteRoom(room.getId());
            model.addAttribute("error", "room_not_found");
            model.addAttribute("room", null);
            user.setRoomNumber("N/A");
            user.setRoomType("N/A");
            userRepo.save(user);
            return "myroom";
        }
        System.out.println("enter in this state********");
        model.addAttribute("room", room);
        return "myroom";
    }

    @PostMapping("/apply-room")
    public String applyRoom(@RequestParam("roomNumber") String roomNumber,
            @RequestParam("floor") String floor, @RequestParam("buildingName") String buildingName,
            @RequestParam("roomType") String roomType,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userRepo.findUserByUserEmail(authentication.getName());
        MyRoom room = myRoomRepo.findMyRoomByUser(user);

        
        if (room == null) {
            room = new MyRoom();
        }else{
            redirectAttributes.addFlashAttribute("warning","warning");
            return "redirect:/myroom";
        }
        room.setRoomNumber(roomNumber);
        room.setBuildingName(buildingName);
        room.setDate(currDate());
        room.setFloor(floor);
        room.setRoomType(roomType);
        room.setStatus("Pending");
        room.setUser(user);
        myRoomRepo.save(room);
        redirectAttributes.addFlashAttribute("room", room);
        return "redirect:/myroom";
    }

    public String currDate() {
        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the current date
        String formattedDate = currentDate.format(formatter);
        return formattedDate;

    }

}