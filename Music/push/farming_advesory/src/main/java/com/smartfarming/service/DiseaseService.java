package com.smartfarming.service;

import com.smartfarming.entity.Disease;
import com.smartfarming.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService {
    
    @Autowired
    private DiseaseRepository diseaseRepository;
    
    public List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }
    
    public Optional<Disease> getDiseaseById(Long id) {
        return diseaseRepository.findById(id);
    }
    
    public Disease saveDisease(Disease disease) {
        return diseaseRepository.save(disease);
    }
    
    public Disease updateDisease(Long id, Disease diseaseDetails) {
        return diseaseRepository.findById(id)
            .map(disease -> {
                disease.setSymptoms(diseaseDetails.getSymptoms());
                disease.setPreventionMethod(diseaseDetails.getPreventionMethod());
                disease.setMedicineType(diseaseDetails.getMedicineType());
                return diseaseRepository.save(disease);
            })
            .orElseThrow(() -> new RuntimeException("Disease not found with id: " + id));
    }
    
    public void deleteDisease(Long id) {
        if (!diseaseRepository.existsById(id)) {
            throw new RuntimeException("Disease not found with id: " + id);
        }
        diseaseRepository.deleteById(id);
    }
    
    public List<Disease> searchDiseasesBySymptoms(String symptom) {
        return diseaseRepository.findBySymptomsContaining(symptom);
    }
    
    public List<Disease> getDiseasesByMedicineType(String medicineType) {
        return diseaseRepository.findByMedicineType(medicineType);
    }
    
    public List<Disease> getDiseasesWithPreventionMethods() {
        return diseaseRepository.findByPreventionMethodIsNotNull();
    }
}
