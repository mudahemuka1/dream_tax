package com.smartfarming.repository;

import com.smartfarming.entity.Agronomist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgronomistRepository extends JpaRepository<Agronomist, Long> {
    
    Optional<Agronomist> findByPhone(String phone);
    
    boolean existsByPhone(String phone);
}
