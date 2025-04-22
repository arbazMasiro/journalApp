package com.ahusain.journalapp.service;

import com.ahusain.journalapp.exception.UserNotSavedException;
import com.ahusain.journalapp.model.JournalEntry;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void saveUser(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("Please Enter Name");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Please Enter Password");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Please Enter Valid Email");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Please Enter Role");
        }
        user.setUserName(user.getUserName());
        user.setEmail(user.getEmail());
        user.setRole(user.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser2(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotSavedException("No user found"));
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    @Transactional
    public User deleteUserWithEntry(String userName) {
        Optional<User> userFoundByName = userRepository.findByUserName(userName);
        if (userFoundByName.isPresent()) {
            userRepository.deleteByUserName(userName);  // This will also delete the associated journal entries
            return userFoundByName.get();  // Return the deleted user or some other response
        }
        throw new UserNotSavedException("This User Deleted Already");
    }

    public User getUserWithEntries(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotSavedException("User Not Found"));
    }

    public List<User> getUserForSentimentalAnalysis() {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return userRepository.getUserForSA(emailRegex);
    }


    public User updateJournalEntry(String userName, Long journalEntryId, JournalEntry updatedEntry) {
        // Step 1: Find the user by username
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotSavedException("User Not Found"));

        // Step 2: Find the journal entry by ID
        JournalEntry journalEntry = user.getJournalEntries().stream()
                .filter(entry -> Objects.equals(entry.getId(), journalEntryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Journal Entry Not Found"));

        // Step 3: Update the journal entry details
        if (updatedEntry.getTitle() != null) {
            journalEntry.setTitle(updatedEntry.getTitle());
        }
        if (updatedEntry.getContent() != null) {
            journalEntry.setContent(updatedEntry.getContent());
        }
        if (updatedEntry.getDate() != null) {
            journalEntry.setDate(updatedEntry.getDate());
        }
        // Step 4: Save the updated user (since journal entries are linked to user)
        return userRepository.save(user);
    }


    public List<User> getUserWithNoEntries() {
        List<User> all = userRepository.findAll();
        List<User> usersWithNoEntries = new ArrayList<>();
        for (User allNo : all) {
            if (allNo.getJournalEntries() != null && allNo.getJournalEntries().isEmpty()) {
                usersWithNoEntries.add(allNo);
            }
        }
        if (usersWithNoEntries.isEmpty()) {
            throw new UserNotSavedException("No User Found with Empty Journal");
        }
        return usersWithNoEntries;
    }


    //Admin WIll Access This
    public List<User> getAllEntries() {
        List<User> all = userRepository.findAll();
        if (all.isEmpty()) {
            throw new UserNotSavedException("No User Found");
        }
      return all;
    }

    public List<User> deleteAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotSavedException("No users found to delete.");
        }
        userRepository.deleteAll();
        return users;
    }

}
