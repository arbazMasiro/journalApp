package com.ahusain.journalapp.controller;

import com.ahusain.journalapp.cache.APPCache;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final APPCache appCache;

    @Autowired
    public AdminController(UserService userService, APPCache appCache) {
        this.userService = userService;
        this.appCache = appCache;
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUsersWithJournalEntries() {
        List<User> all = userService.getAllEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/all-user")
    public ResponseEntity<List<User>> deleteAllUser() {
        List<User> delete = userService.deleteAllUser();
        if (delete != null && !delete.isEmpty()) {
            return new ResponseEntity<>(delete, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/userWithNoJournal")
    public ResponseEntity<?> getAllUserWithEmptyJournal() {
        List<User> userWithNoEntries = userService.getUserWithNoEntries();

        if (userWithNoEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User With Empty Journal");
        }
        return new ResponseEntity<>(userWithNoEntries, HttpStatus.OK);
    }


    @GetMapping("/clear-cache")
    @Scheduled(cron = "0 */5 * ? * *")
    public void clearCache(){
        appCache.init();
    }
}
