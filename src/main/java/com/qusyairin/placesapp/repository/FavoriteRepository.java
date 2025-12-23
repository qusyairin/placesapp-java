package com.qusyairin.placesapp.repository;

import com.qusyairin.placesapp.model.FavoritePlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoritePlace, Long> {
    
    Page<FavoritePlace> findAllByUserId(String userId, Pageable pageable);
    
    boolean existsByPlaceIdAndUserId(String placeId, String userId);
}