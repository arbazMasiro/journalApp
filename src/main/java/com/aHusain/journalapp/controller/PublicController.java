package com.ahusain.journalapp.controller;


import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.UserRepository;
import com.ahusain.journalapp.service.EmailService;
import com.ahusain.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public PublicController(UserService userService, UserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/adduser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Check if user with same username already exists
            if (userRepository.existsByUserName(user.getUserName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already Created");
            }
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        emailService.emailSender(to, body, subject);
        return "Email sent (or attempted) to: " + to;
    }

}
