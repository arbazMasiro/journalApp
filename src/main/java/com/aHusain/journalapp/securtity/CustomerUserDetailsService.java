package com.ahusain.journalapp.securtity;

import com.ahusain.journalapp.exception.UserNotSavedException;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.JournalEntryRepository;
import com.ahusain.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotSavedException("User not found"));
        return new CustomUserDetails(user);
    }
}
