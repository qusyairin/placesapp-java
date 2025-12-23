package com.qusyairin.placesapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GooglePlacesService {
    
    private final RestTemplate restTemplate;
    
    @Value("${google.api.key}")
    private String apiKey;
    
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
    
    public Map<String, Object> searchPlaces(String query) {
        log.info("Searching places with query: {}", query);
        
        String url = PLACES_API_URL + "?input=" + query + "&key=" + apiKey + "&types=geocode";
        
        try {
            Map response = restTemplate.getForObject(url, Map.class);
            log.info("Google API call successful");
            return response;
        } catch (Exception e) {
            log.error("Error calling Google Places API", e);
            throw new RuntimeException("Failed to search places", e);
        }
    }
}