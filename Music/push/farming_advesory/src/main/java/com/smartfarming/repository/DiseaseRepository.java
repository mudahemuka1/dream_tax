package com.smartfarming.repository;

import com.smartfarming.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    
    @Query("SELECT d FROM Disease d WHERE d.symptoms LIKE %:symptom%")
    List<Disease> findBySymptomsContaining(@Param("symptom") String symptom);
    
    @Query("SELECT d FROM Disease d WHERE d.medicineType = :medicineType")
    List<Disease> findByMedicineType(@Param("medicineType") String medicineType);
    
    List<Disease> findByPreventionMethodIsNotNull();
}
