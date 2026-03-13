package com.smartfarming.repository;

import com.smartfarming.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    
    @Query("SELECT f FROM Fertilizer f WHERE f.nutrientContent LIKE %:nutrient%")
    List<Fertilizer> findByNutrientContentContaining(@Param("nutrient") String nutrient);
    
    List<Fertilizer> findByRecommendedDoseContaining(String dose);
}
