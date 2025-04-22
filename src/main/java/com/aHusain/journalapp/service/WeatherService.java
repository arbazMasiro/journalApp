package com.ahusain.journalapp.service;

import com.ahusain.journalapp.apiresponse.WeatherResponse;
import com.ahusain.journalapp.cache.APPCache;
import com.ahusain.journalapp.util.Keys;
import com.ahusain.journalapp.util.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${Weather.API_KEY}")
    public String apiKey;

    private final RestTemplate restTemplate;
    private final APPCache appCache;

    @Autowired
    public WeatherService(RestTemplate restTemplate, APPCache appCache) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.APP_CACHE.get(Keys.WEATHER_API.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
