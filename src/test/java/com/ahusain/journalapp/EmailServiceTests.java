package com.ahusain.journalapp;

import com.ahusain.journalapp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("dev")
@SpringBootTest
class EmailServiceTests {
    @Autowired
    public EmailService emailService;

    String to = "masiroarbaz@gmail.com";

    @Test
    void testEmailSending() {
        emailService.emailSender(to, "Test Subject", "Test Body");
    }

}

