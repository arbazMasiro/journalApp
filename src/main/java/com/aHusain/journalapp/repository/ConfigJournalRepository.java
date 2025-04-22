package com.ahusain.journalapp.repository;

import com.ahusain.journalapp.model.ConfigJournalApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalRepository extends JpaRepository<ConfigJournalApp, Long> {

}
