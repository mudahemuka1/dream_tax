package com.smartfarming.repository;

import com.smartfarming.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    
    Optional<Farmer> findByPhoneNumber(String phoneNumber);
    
    List<Farmer> findByLocationContainingIgnoreCase(String location);
    
    @Query("SELECT f FROM Farmer f WHERE f.location = :location")
    List<Farmer> findByLocation(@Param("location") String location);
    
    boolean existsByPhoneNumber(String phoneNumber);
}
