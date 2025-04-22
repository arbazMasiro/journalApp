package com.ahusain.journalapp.controller;

import com.ahusain.journalapp.apiresponse.WeatherResponse;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.service.UserService;
import com.ahusain.journalapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final WeatherService weatherService;

    @Autowired
    public UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userForUpdate = userService.findByUsername(userName);
                userForUpdate.setUserName(user.getUserName());
                userForUpdate.setPassword(user.getPassword());
                userForUpdate.setEmail(user.getEmail());
                userForUpdate.setRole(user.getRole());
                userForUpdate.setSentimentalAnalysis(user.isSentimentalAnalysis());
                userService.saveUser(userForUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(userForUpdate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One error occurred: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserWithJournalEntry() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserWithEntries(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           userService.deleteByUserName(authentication.getName());
           return ResponseEntity.ok("User Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/weather")
    public ResponseEntity<?> greeting() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            WeatherResponse response = weatherService.getWeather("Mumbai");
            String greeting = "";
            if (response != null){
                greeting =  "Weather feels like:\n"
                        + response.getRequest() + "\n"
                        + response.getLocation() + "\n"
                        + response.getCurrent();
            }
            return new ResponseEntity<>("Hii " + authentication.getName() + ", " + greeting, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
        }
    }

}
