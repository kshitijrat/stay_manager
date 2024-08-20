package com.practicespring.stay_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practicespring.stay_manager.entities.BankAccount;
import com.practicespring.stay_manager.entities.MyRoom;
import com.practicespring.stay_manager.entities.Payment;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.MyRoomRepo;
import com.practicespring.stay_manager.repo.PaymentRepo;
import com.practicespring.stay_manager.repo.UserRepo;
import com.practicespring.stay_manager.services.BankAccountService;
import com.practicespring.stay_manager.services.MyRoomService;



@Controller
public class EndPointController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private MyRoomRepo myRoomRepo;

    @Autowired
    private MyRoomService myRoomService;


    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/index")
    public String login() {
        return "index";
    }

    @GetMapping("/dashboard_admin")
    public String showDashboardAdmin() {
        return "dashboard_admin";
    }

    @GetMapping("/bank_account")
    public String showBankAccount(Model model){
        model.addAttribute("bankAccount", new BankAccount());
        model.addAttribute("bankAccounts", bankAccountService.getAllBankAccounts());
        return "bank_account";
    }

    

    @GetMapping("/settings")
    public String showSettings(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        model.addAttribute("user", user);
        System.out.println("Show setting run...................");
        return "settings";
    }


    @GetMapping("/login")
    public String showLoginPage2() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }


    @GetMapping("/payment_report")
    public String showPaymentReport(Authentication authentication, Model model){

        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        List<Payment> payment = paymentRepo.findByUser(user);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        model.addAttribute("payments", payment);
        model.addAttribute("user",user);
        model.addAttribute("myroom",myRoom);
        return "payment_report";

    }

    @GetMapping("/room_report")
    public String showRoomReport(Authentication authentication, Model model){
        String email = authentication.getName();
        User user = userRepository.findUserByUserEmail(email);
        MyRoom myRoom = myRoomRepo.findMyRoomByUser(user);
        model.addAttribute("user",user);
        model.addAttribute("myroom",myRoom);
        return "room_report";
    }


    @GetMapping("/requestformadmin")
    public String showRequestForm(Authentication authentication, Model model){
        List<User> userList = myRoomService.getPendingRequestUser();
        List<MyRoom> myRoomsList = myRoomRepo.findByStatus("Pending");
        model.addAttribute("myRoomList", myRoomsList);
        model.addAttribute("userList", userList);
        int max = Math.max(userList.size(), myRoomsList.size());
        model.addAttribute("max", max);
        return "requestformadmin";
    }


    @GetMapping("/manage_student")
    public String showMangeStudent(){
        return "manage_student";
    }
   
    @GetMapping("/manage_rooms")
    public String showMangeRooms(){
        return "manage_rooms";
    }
    @GetMapping("/manage_bookings")
    public String showMangeBookings(){
        return "manage_bookings";
    }
    @GetMapping("/admin_setting")
    public String showAdminSetting(){
        return "admin_setting";
    }

    

}
