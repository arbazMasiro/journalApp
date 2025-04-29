package com.ahusain.journalapp.service;

import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @Test
    void testFindByUserName() {
//        assertEquals(4, 2+2);
        Optional<User> user = userRepository.findByUserName("Amir");
        assertTrue(!user.get().getJournalEntries().isEmpty());

    }

    @BeforeEach
    void setup(){
        System.out.println("before from each test");
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    void testFindNewUserName(User name){
        User user = userService.saveUser(name);
        assertNotNull(user.getUserId());
        assertNotNull(user);
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "19, 2, 21",
            "12, 2, 15"
    })
    void test(int a, int b, int expected){
        assertEquals(expected, a+b);

    }
}
