package com.ahusain.journalapp.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    @JsonProperty("request")
    private Request request;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("current")
    private Current current;


    @Getter
    @Setter
    public static class Request {
        @JsonProperty("type")
        private String type;

        @JsonProperty("query")
        private String query;

        @JsonProperty("language")
        private String language;

        @JsonProperty("unit")
        private String unit;

        @Override
        public String toString() {
            return "Request{" +
                    "type='" + type + '\'' +
                    ", query='" + query + '\'' +
                    ", language='" + language + '\'' +
                    ", unit='" + unit + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class Location {
        @JsonProperty("name")
        private String name;

        @JsonProperty("country")
        private String country;

        @JsonProperty("region")
        private String region;

        @JsonProperty("lat")
        private String lat;

        @JsonProperty("lon")
        private String lon;

        @JsonProperty("timezone_id")
        private String timezoneId;

        @JsonProperty("localtime")
        private String localtime;

        @JsonProperty("localtime_epoch")
        private int localtimeEpoch;

        @JsonProperty("utc_offset")
        private String utcOffset;

        @Override
        public String toString() {
            return "Location{" +
                    "name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    ", region='" + region + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lon='" + lon + '\'' +
                    ", timezoneId='" + timezoneId + '\'' +
                    ", localtime='" + localtime + '\'' +
                    ", localtimeEpoch=" + localtimeEpoch +
                    ", utcOffset='" + utcOffset + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class Current {
        @JsonProperty("observation_time")
        private String observationTime;

        @JsonProperty("temperature")
        private int temperature;

        @JsonProperty("weather_code")
        private int weatherCode;

        @JsonProperty("weather_icons")
        private List<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        @JsonProperty("astro")
        private Astro astro;

        @JsonProperty("air_quality")
        private AirQuality airQuality;

        @JsonProperty("wind_speed")
        private int windSpeed;

        @JsonProperty("wind_degree")
        private int windDegree;

        @JsonProperty("wind_dir")
        private String windDir;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("precip")
        private int precip;

        @JsonProperty("humidity")
        private int humidity;

        @JsonProperty("cloudcover")
        private int cloudcover;

        @JsonProperty("feelslike")
        private int feelslike;

        @JsonProperty("uv_index")
        private int uvIndex;

        @JsonProperty("visibility")
        private int visibility;

        @JsonProperty("is_day")
        private String isDay;

        @Override
        public String toString() {
            return "Current{" +
                    "observationTime='" + observationTime + '\'' +
                    ", temperature=" + temperature +
                    ", weatherCode=" + weatherCode +
                    ", weatherIcons=" + weatherIcons +
                    ", weatherDescriptions=" + weatherDescriptions +
                    ", windSpeed=" + windSpeed +
                    ", windDegree=" + windDegree +
                    ", windDir='" + windDir + '\'' +
                    ", pressure=" + pressure +
                    ", precip=" + precip +
                    ", humidity=" + humidity +
                    ", cloudcover=" + cloudcover +
                    ", feelslike=" + feelslike +
                    ", uvIndex=" + uvIndex +
                    ", visibility=" + visibility +
                    ", isDay='" + isDay + '\'' +
                    ", astro=" + astro +
                    ", airQuality=" + airQuality +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class Astro {
        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        @JsonProperty("moonrise")
        private String moonrise;

        @JsonProperty("moonset")
        private String moonset;

        @JsonProperty("moon_phase")
        private String moonPhase;

        @JsonProperty("moon_illumination")
        private int moonIllumination;

        @Override
        public String toString() {
            return "Astro{" +
                    "sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    ", moonrise='" + moonrise + '\'' +
                    ", moonset='" + moonset + '\'' +
                    ", moonPhase='" + moonPhase + '\'' +
                    ", moonIllumination=" + moonIllumination +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class AirQuality {
        @JsonProperty("co")
        private String co;

        @JsonProperty("no2")
        private String no2;

        @JsonProperty("o3")
        private String o3;

        @JsonProperty("so2")
        private String so2;

        @JsonProperty("pm2_5")
        private String pm2_5;

        @JsonProperty("pm10")
        private String pm10;

        @JsonProperty("us-epa-index")
        private String usEpaIndex;

        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;

        @Override
        public String toString() {
            return "AirQuality{" +
                    "co='" + co + '\'' +
                    ", no2='" + no2 + '\'' +
                    ", o3='" + o3 + '\'' +
                    ", so2='" + so2 + '\'' +
                    ", pm2_5='" + pm2_5 + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", usEpaIndex='" + usEpaIndex + '\'' +
                    ", gbDefraIndex='" + gbDefraIndex + '\'' +
                    '}';
        }
    }
}
