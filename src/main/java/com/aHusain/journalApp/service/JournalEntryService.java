package com.aHusain.journalApp.service;

import com.aHusain.journalApp.model.JournalEntry;
import com.aHusain.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getJournalEntry(Long id) {
        return journalEntryRepository.findById(id);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public List<JournalEntry> deleteAllEntries() {
        List<JournalEntry> all = journalEntryRepository.findAll();
        journalEntryRepository.deleteAll();
        return all;

    }

    public Optional<JournalEntry> deleteEntry(Long id) {
        Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
        journalEntryRepository.deleteById(id);
        return journalEntry;

    }

    public List<JournalEntry> updateEntry(Long id, JournalEntry journalEntry) {
        List<JournalEntry> update = journalEntryRepository.findAll();
        for (JournalEntry entry : update) {
            if (entry.getId() == id) {
                entry.setTitle(journalEntry.getTitle());
                entry.setContent(journalEntry.getContent());
                journalEntryRepository.save(entry);
            }
        }
        return update;
    }


}
