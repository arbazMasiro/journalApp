package com.ahusain.journalapp;

import com.ahusain.journalapp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTests {

    private final UserScheduler userScheduler;

    @Autowired
    public UserSchedulerTests(UserScheduler userScheduler) {
        this.userScheduler = userScheduler;
    }

    @Test
    void testUserScheduled(){
        userScheduler.fetchUsersForSAMail();
    }
}
