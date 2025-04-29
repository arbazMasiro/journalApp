package com.ahusain.journalapp.service;

import com.ahusain.journalapp.exception.UserNotSavedException;
import com.ahusain.journalapp.model.JournalEntry;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {


    private final JournalEntryRepository journalEntryRepository;

    private final UserService userService;

    @Autowired
    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    public void saveEntry(String username, JournalEntry journalEntry) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotSavedException("User not found: " + username);
        }

        try {
            journalEntry.setUser(user);
            journalEntry.setDate(LocalDateTime.now());
            if (journalEntry.getSentiment() == null || journalEntry.getSentiment().toString().isEmpty()){
                log.error("Error Occurred for Sentiment {} , please provide your sentiment" , journalEntry.getSentiment());
                throw new IllegalArgumentException("Sentiment is required. Please provide your sentiment.");
            }
            journalEntry.setSentiment(journalEntry.getSentiment());
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveUser2(user);
            userService.findByUsername(username);
        } catch (Exception e) {
            throw new UserNotSavedException("Failed to save entry: " + e.getMessage());
        }
    }

    public void saveEntry2(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(Long id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteJournalById(String uerName, Long id){
       boolean removed;
        try {
           User user = userService.findByUsername(uerName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            journalEntryRepository.deleteById(id);
            if (removed){
               userService.saveUser2(user);
           }
       }catch (Exception e){
           throw new ExpressionException("An error occurred while deleting the Entry:");
       }
        return removed;
    }
}
