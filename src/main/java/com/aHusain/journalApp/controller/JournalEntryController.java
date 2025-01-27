package com.aHusain.journalApp.controller;

import com.aHusain.journalApp.model.JournalEntry;
import com.aHusain.journalApp.model.User;
import com.aHusain.journalApp.service.JournalEntryService;
import com.aHusain.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntries() {
        List<JournalEntry> all = journalEntryService.getAllEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is Not Data available in the Database!!!!!");
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"Data Not posted\"");
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable Long myId) {
        Optional<JournalEntry> entry = journalEntryService.getJournalEntry(myId);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Now the Database is empty");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllJournalEntries() {
        List<JournalEntry> delete = journalEntryService.deleteAllEntries();
        if (delete != null && !delete.isEmpty()) {
            return new ResponseEntity<>(delete, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"Now the Database is Empty\"");
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.deleteEntry(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\"There is not Data Available in Database\"");
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> UpdateJournalEntry(@PathVariable Long id, @RequestBody JournalEntry journalEntry) {
        if (!id.equals(journalEntry.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"Path ID and JournalEntry ID do not match");
        }

        List<JournalEntry> all = journalEntryService.getAllEntries();
        if (all != null && !all.isEmpty()) {
            journalEntryService.updateEntry(id, journalEntry);
            return ResponseEntity.ok("the journal entry updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"There are Some error in The Server\"");
    }


}
