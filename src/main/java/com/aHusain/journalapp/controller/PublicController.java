package com.ahusain.journalapp.controller;


import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.UserRepository;
import com.ahusain.journalapp.service.EmailService;
import com.ahusain.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Check if user with same username already exists
            if (userRepository.existsByUserName(user.getUserName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already Created");
            }
            User createdUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        String verify = userService.verify(user);
        if (verify != null && !verify.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("Token", verify);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
