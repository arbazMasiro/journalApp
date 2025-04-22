package com.ahusain.journalapp.cache;

import com.ahusain.journalapp.model.ConfigJournalApp;
import com.ahusain.journalapp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class APPCache {

    private final ConfigJournalRepository configJournalRepository;

    @Autowired
    public APPCache(ConfigJournalRepository configJournalRepository) {
        this.configJournalRepository = configJournalRepository;
    }
    public Map<String, String> APP_CACHE;

    @PostConstruct
    public void init() {
        APP_CACHE = new HashMap<>();
        List<ConfigJournalApp> all = configJournalRepository.findAll();
        for (ConfigJournalApp configJournalApp : all)
            APP_CACHE.put(configJournalApp.getKey().name(), configJournalApp.getValue());
    }
}
