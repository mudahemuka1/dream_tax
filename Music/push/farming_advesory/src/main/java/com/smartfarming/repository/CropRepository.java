package com.smartfarming.repository;

import com.smartfarming.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    
    Optional<Crop> findByCropNameIgnoreCase(String cropName);
    
    List<Crop> findByIdealSoilIgnoreCase(String soilType);
    
    List<Crop> findByIdealSeasonIgnoreCase(String season);
    
    @Query("SELECT c FROM Crop c WHERE c.idealSoil = :soil AND c.idealSeason = :season")
    List<Crop> findBySoilAndSeason(@Param("soil") String soil, @Param("season") String season);
    
    boolean existsByCropNameIgnoreCase(String cropName);
}
