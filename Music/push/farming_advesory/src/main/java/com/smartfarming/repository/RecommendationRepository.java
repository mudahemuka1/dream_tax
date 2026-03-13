package com.smartfarming.repository;

import com.smartfarming.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    
    List<Recommendation> findByFarmerFarmerId(Long farmerId);
    
    List<Recommendation> findByCropCropId(Long cropId);
    
    List<Recommendation> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT r FROM Recommendation r WHERE r.farmer.location = :location")
    List<Recommendation> findByFarmerLocation(@Param("location") String location);
    
    @Query("SELECT COUNT(r) FROM Recommendation r WHERE r.farmer.farmerId = :farmerId")
    Long countByFarmerId(@Param("farmerId") Long farmerId);
}
