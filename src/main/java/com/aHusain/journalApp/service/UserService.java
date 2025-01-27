package com.aHusain.journalApp.service;

import com.aHusain.journalApp.model.User;
import com.aHusain.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public List<User> getAllEntries() {
        return userRepository.findAll();
    }

    public User findByUsername(String userName){
        return userRepository.findByUserName(userName);
    }


}
