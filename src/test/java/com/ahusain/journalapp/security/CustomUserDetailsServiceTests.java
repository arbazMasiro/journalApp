package com.ahusain.journalapp.security;

import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.UserRepository;
import com.ahusain.journalapp.securtity.CustomUserDetailsService;
import com.ahusain.journalapp.util.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTests {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUserName(anyString()))
                .thenReturn(Optional.ofNullable(User.builder()
                        .userName("Amir")
                        .password("stdhgrtgrgdfgf")
                        .role(Role.USER)
                        .build()));

        UserDetails userDetails = userDetailsService.loadUserByUsername("Amir");
        Assertions.assertNotNull(userDetails);
    }
}
