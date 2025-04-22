package com.ahusain.journalapp.repository;

import com.ahusain.journalapp.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    //    @Modifying
//    @Transactional
//    @Query("UPDATE JournalEntry j SET j.date = :date WHERE j.id = :id")
//    void setDate(@Param("date") LocalDateTime date);

}
