package com.practicespring.stay_manager.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicespring.stay_manager.entities.Feedback;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.FeedbackRepo;
import com.practicespring.stay_manager.repo.UserRepo;



@Controller
public class FeedbackController {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    @PostMapping("/feedbackform")
    public String submitFeedback(@RequestParam("message")String message, Model model, 
    Authentication authentication, RedirectAttributes redirectAttributes){
        Feedback feedback = new Feedback();
        String email = authentication.getName();
        User user = userRepo.findUserByUserEmail(email);
        feedback.setDate(currDate());
        feedback.setMessage(message.trim());
        feedback.setUser(user);
        feedbackRepo.save(feedback);
        System.out.println("Feedback form run........");
        redirectAttributes.addFlashAttribute("success", "feedback");
        return "redirect:/myroom";
    }


    public String currDate(){
         LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);
        return dateString;
    }

}
