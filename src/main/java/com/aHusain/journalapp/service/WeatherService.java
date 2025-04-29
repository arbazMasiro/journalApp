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
    private final RedisService redisService;

    @Autowired
    public WeatherService(RestTemplate restTemplate, APPCache appCache, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
        this.redisService = redisService;
    }

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.getDataFromRedisLayer("Weather Of " + city, WeatherResponse.class);
        if (weatherResponse != null)
            return weatherResponse;
        else {
            String finalAPI = appCache.APP_CACHE.get(Keys.WEATHER_API.toString()).replace(PlaceHolders.CITY.getValue(), city).replace(PlaceHolders.API_KEY.getValue(), apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.setDataIntoRedisLayer("Weather Of " + city, body, 300L);
            }
            return body;
        }
    }
}
