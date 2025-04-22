package com.ahusain.journalapp.controller;

import com.ahusain.journalapp.exception.UserNotSavedException;
import com.ahusain.journalapp.model.JournalEntry;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.service.JournalEntryService;
import com.ahusain.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    private final JournalEntryService journalEntryService;
    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }


    @GetMapping("id/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable("journalId") Long myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserWithEntries(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addingEntriesInUsersJournalEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(userName, journalEntry);
            User user = userService.getUserWithEntries(userName);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entry Not posted in Database");
        }
    }


    @DeleteMapping("id/{userId}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable("userId") Long userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean removed = journalEntryService.deleteJournalById(userName, userId);
            if (removed){
            return ResponseEntity.ok("JournalEntry Deleted Successfully By Id");
            }
        } catch (UserNotSavedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deleted Already");
    }

    @PutMapping("id/{journalEntryId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable Long journalEntryId, @RequestBody JournalEntry updatedEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userForUpdate = userService.findByUsername(userName);
            List<JournalEntry> collect = userForUpdate.getJournalEntries().stream().filter(x -> x.getId().equals(journalEntryId)).toList();
            if (!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(journalEntryId);
                if (journalEntry.isPresent()){
                    JournalEntry update = journalEntry.get();
                    update.setTitle(updatedEntry.getTitle());
                    update.setContent(updatedEntry.getContent());
                    update.setDate(LocalDateTime.now());
                    update.setSentiment(updatedEntry.getSentiment());
                    journalEntryService.saveEntry2(update);
                    return new ResponseEntity<>(update, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("An Error occurred when updating the journal-entry");
    }



}
