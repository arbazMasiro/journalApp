package com.ahusain.journalapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceHolders {

    CITY("<city>"),
    API_KEY("<apiKey>");

    private final String value;
}