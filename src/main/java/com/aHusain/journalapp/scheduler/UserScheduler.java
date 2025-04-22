package com.ahusain.journalapp.scheduler;

import com.ahusain.journalapp.model.JournalEntry;
import com.ahusain.journalapp.model.User;
import com.ahusain.journalapp.service.EmailService;
import com.ahusain.journalapp.service.UserService;
import com.ahusain.journalapp.util.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserScheduler {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserScheduler(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 ? * SUN")
//    @Scheduled(cron = "0 * * ? * *")
    public void fetchUsersForSAMail() {
        List<User> userForSA = userService.getUserForSentimentalAnalysis();
        for (User user : userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getSentiment()).toList();
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.emailSender(user.getEmail(), "Sentimental For Last 7 Days", mostFrequentSentiment.toString());
            }
        }

    }
}
