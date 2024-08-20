package com.practicespring.stay_manager.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;
import com.practicespring.stay_manager.repo.UserRepo;



@Controller
public class DashboardController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private  MyRoomRepo myRoomRepo;


    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findUserByUserEmail(authentication.getName());
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            // Set dummy activity data for demonstration
            user.setActivityDate1(currentDate.minusDays(5).format(formatter));
            user.setActivityDate2(currentDate.minusDays(2).format(formatter));
            user.setActivityDate3(formattedDate);

            LocalDate upcomingBookingDate = currentDate.plusDays(10);
            user.setUpcomingBookingId("BKG12345"); // Example booking ID
            user.setUpcomingBookingDate(upcomingBookingDate.format(formatter));

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                model.addAttribute("user", user);
                List<User> totalUsersList = userRepository.findUserByRoles("ROLE_USER");
                model.addAttribute("totalStudents", totalUsersList.size());
                List<MyRoom> pendingrequestList = myRoomRepo.findByStatus("Pending");
                model.addAttribute("pendingRequests", pendingrequestList.size());
                
                System.out.println("Enter in dashbord controller..............");
                return "dashboard_admin"; // Admin dashboard
            } else {
                model.addAttribute("user", user);
                return "dashboard"; // Normal user dashboard
            }
        }
        System.out.println("Error::::::::::::::");
        return "redirect:/index";
    }

}
