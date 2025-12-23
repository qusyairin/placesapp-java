package com.qusyairin.placesapp.service;

import com.qusyairin.placesapp.dto.FavoritePlaceDto;
import com.qusyairin.placesapp.model.FavoritePlace;
import com.qusyairin.placesapp.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    
    @Transactional(readOnly = true)
    public Page<FavoritePlace> getFavorites(String userId, int page, int size) {
        log.info("Getting favorites for user: {}", userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return favoriteRepository.findAllByUserId(userId, pageable);
    }
    
    @Transactional
    public FavoritePlace addFavorite(FavoritePlaceDto dto, String userId) {
        log.info("Adding favorite: {}", dto.getPlaceName());
        
        if (favoriteRepository.existsByPlaceIdAndUserId(dto.getPlaceId(), userId)) {
            throw new RuntimeException("Place already in favorites");
        }
        
        FavoritePlace favorite = new FavoritePlace();
        favorite.setPlaceId(dto.getPlaceId());
        favorite.setPlaceName(dto.getPlaceName());
        favorite.setAddress(dto.getAddress());
        favorite.setLatitude(dto.getLatitude());
        favorite.setLongitude(dto.getLongitude());
        favorite.setUserId(userId);
        
        return favoriteRepository.save(favorite);
    }
    
    @Transactional
    public FavoritePlace updateFavorite(Long id, FavoritePlaceDto dto, String userId) {
        log.info("Updating favorite: {}", id);
        
        FavoritePlace favorite = favoriteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorite not found"));
            
        if (!favorite.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        favorite.setPlaceName(dto.getPlaceName());
        favorite.setAddress(dto.getAddress());
        favorite.setLatitude(dto.getLatitude());
        favorite.setLongitude(dto.getLongitude());
        
        return favoriteRepository.save(favorite);
    }
    
    @Transactional
    public void deleteFavorite(Long id, String userId) {
        log.info("Deleting favorite: {}", id);
        
        FavoritePlace favorite = favoriteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorite not found"));
            
        if (!favorite.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        favoriteRepository.delete(favorite);
    }
}