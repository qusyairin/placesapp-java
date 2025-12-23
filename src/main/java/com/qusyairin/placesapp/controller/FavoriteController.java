package com.qusyairin.placesapp.controller;

import com.qusyairin.placesapp.dto.FavoritePlaceDto;
import com.qusyairin.placesapp.model.FavoritePlace;
import com.qusyairin.placesapp.service.FavoriteService;
import com.qusyairin.placesapp.service.GooglePlacesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    private final GooglePlacesService googlePlacesService;
    
    @GetMapping("/places/search")
    public ResponseEntity<Map<String, Object>> searchPlaces(@RequestParam String query) {
        log.info("GET /api/places/search - query: {}", query);
        Map<String, Object> result = googlePlacesService.searchPlaces(query);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<Page<FavoritePlace>> getFavorites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(value = "User-Id", defaultValue = "default-user") String userId) {
        
        log.info("GET /api/favorites - page: {}, size: {}", page, size);
        Page<FavoritePlace> favorites = favoriteService.getFavorites(userId, page, size);
        return ResponseEntity.ok(favorites);
    }
    
    @PostMapping("/favorites")
    public ResponseEntity<FavoritePlace> addFavorite(
            @RequestBody FavoritePlaceDto dto,
            @RequestHeader(value = "User-Id", defaultValue = "default-user") String userId) {
        
        log.info("POST /api/favorites - place: {}", dto.getPlaceName());
        FavoritePlace favorite = favoriteService.addFavorite(dto, userId);
        return ResponseEntity.ok(favorite);
    }
    
    @PutMapping("/favorites/{id}")
    public ResponseEntity<FavoritePlace> updateFavorite(
            @PathVariable Long id,
            @RequestBody FavoritePlaceDto dto,
            @RequestHeader(value = "User-Id", defaultValue = "default-user") String userId) {
        
        log.info("PUT /api/favorites/{}", id);
        FavoritePlace favorite = favoriteService.updateFavorite(id, dto, userId);
        return ResponseEntity.ok(favorite);
    }
    
    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long id,
            @RequestHeader(value = "User-Id", defaultValue = "default-user") String userId) {
        
        log.info("DELETE /api/favorites/{}", id);
        favoriteService.deleteFavorite(id, userId);
        return ResponseEntity.noContent().build();
    }
}