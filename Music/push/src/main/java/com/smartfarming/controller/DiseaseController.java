package com.smartfarming.controller;

import com.smartfarming.entity.Disease;
import com.smartfarming.service.DiseaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "*")
public class DiseaseController {
    
    @Autowired
    private DiseaseService diseaseService;
    
    @GetMapping
    public ResponseEntity<List<Disease>> getAllDiseases() {
        List<Disease> diseases = diseaseService.getAllDiseases();
        return ResponseEntity.ok(diseases);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable Long id) {
        return diseaseService.getDiseaseById(id)
            .map(disease -> ResponseEntity.ok(disease))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Disease>> searchDiseasesBySymptoms(@RequestParam String symptom) {
        List<Disease> diseases = diseaseService.searchDiseasesBySymptoms(symptom);
        return ResponseEntity.ok(diseases);
    }
    
    @GetMapping("/medicine/{medicineType}")
    public ResponseEntity<List<Disease>> getDiseasesByMedicineType(@PathVariable String medicineType) {
        List<Disease> diseases = diseaseService.getDiseasesByMedicineType(medicineType);
        return ResponseEntity.ok(diseases);
    }
    
    @GetMapping("/with-prevention")
    public ResponseEntity<List<Disease>> getDiseasesWithPreventionMethods() {
        List<Disease> diseases = diseaseService.getDiseasesWithPreventionMethods();
        return ResponseEntity.ok(diseases);
    }
    
    @PostMapping
    public ResponseEntity<Disease> createDisease(@Valid @RequestBody Disease disease) {
        Disease savedDisease = diseaseService.saveDisease(disease);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisease);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable Long id, @Valid @RequestBody Disease diseaseDetails) {
        try {
            Disease updatedDisease = diseaseService.updateDisease(id, diseaseDetails);
            return ResponseEntity.ok(updatedDisease);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        try {
            diseaseService.deleteDisease(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
