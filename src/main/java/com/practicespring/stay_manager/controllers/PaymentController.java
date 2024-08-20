package com.practicespring.stay_manager.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicespring.stay_manager.entities.BankAccount;
import com.practicespring.stay_manager.entities.Payment;
import com.practicespring.stay_manager.entities.User;
import com.practicespring.stay_manager.repo.BankAccountRepo;
import com.practicespring.stay_manager.services.BankAccountService;
import com.practicespring.stay_manager.services.PaymentService;
import com.practicespring.stay_manager.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/payment")
    public String showPaymentPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Payment> payments = paymentService.findByUser(user);
        model.addAttribute("payments", payments);
        model.addAttribute("bankAccounts", bankAccountService.getAllBankAccounts());
        return "payment";
    }

    @PostMapping("/payment")
    public String submitPayment(@RequestParam("amount") double amount,
            @RequestParam("date") String date,
            @RequestParam("pin") String pin,
            RedirectAttributes redirectAttribute,
            Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Payment payment = new Payment();
        List<BankAccount> bankAccountList = bankAccountRepo.findByUser(user);
        BankAccount primaryAccount = bankAccountList.stream()
                .filter(BankAccount::isPrimaryAccount)
                .findFirst()
                .orElse(null);

        if (primaryAccount == null) {
            // Handle scenario where no primary account is found
            System.out.println("primary account not found*****************");
            redirectAttribute.addFlashAttribute("error", "no_primary_account");
        return "redirect:/payment";
        }

        // Check if the PIN matches the primary bank account
        boolean pinValid = bankAccountList.stream()
                .filter(BankAccount::isPrimaryAccount)
                .anyMatch(account -> passwordEncoder.matches(pin, account.getPin()));

        if (!pinValid) {
            // Handle invalid PIN scenario (redirect or show an error)
            System.out.println("pin not match**************");
            redirectAttribute.addFlashAttribute("error", "invalid_pin");
        return "redirect:/payment";

        }

        payment.setPaymentDate(date.toString());
        payment.setAmount(amount);
        payment.setUser(user);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setStatus("Pending");
        paymentService.save(payment); // Ensure you save the payment
        redirectAttribute.addFlashAttribute("success", "success_payment");
        return "redirect:/payment";
    }
}
