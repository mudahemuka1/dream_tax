package com.smartfarming.repository;

import com.smartfarming.entity.AgroDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgroDealerRepository extends JpaRepository<AgroDealer, Long> {
    
    Optional<AgroDealer> findByPhone(String phone);
    
    @Query("SELECT a FROM AgroDealer a WHERE a.shopLocation LIKE %:location%")
    List<AgroDealer> findByShopLocationContaining(@Param("location") String location);
    
    boolean existsByPhone(String phone);
}
