package com.qusyairin.placesapp.dto;

import lombok.Data;

@Data
public class FavoritePlaceDto {
    
    private String placeId;
    private String placeName;
    private String address;
    private Double latitude;
    private Double longitude;
}