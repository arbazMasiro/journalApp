package com.ahusain.journalapp.model;

import com.ahusain.journalapp.util.Keys;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "config_journal_app")
public class ConfigJournalApp {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "api_key")
    private Keys key;
    @Column(name = "api_value")
    private String value;

}
