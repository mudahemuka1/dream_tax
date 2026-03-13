package com.smartfarming.controller;

import com.smartfarming.entity.Fertilizer;
import com.smartfarming.service.FertilizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fertilizers")
@CrossOrigin(origins = "*")
public class FertilizerController {
    
    @Autowired
    private FertilizerService fertilizerService;
    
    @GetMapping
    public ResponseEntity<List<Fertilizer>> getAllFertilizers() {
        List<Fertilizer> fertilizers = fertilizerService.getAllFertilizers();
        return ResponseEntity.ok(fertilizers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fertilizer> getFertilizerById(@PathVariable Long id) {
        return fertilizerService.getFertilizerById(id)
            .map(fertilizer -> ResponseEntity.ok(fertilizer))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Fertilizer>> searchFertilizersByNutrient(@RequestParam String nutrient) {
        List<Fertilizer> fertilizers = fertilizerService.searchFertilizersByNutrient(nutrient);
        return ResponseEntity.ok(fertilizers);
    }
    
    @GetMapping("/dose/{dose}")
    public ResponseEntity<List<Fertilizer>> getFertilizersByDose(@PathVariable String dose) {
        List<Fertilizer> fertilizers = fertilizerService.getFertilizersByDose(dose);
        return ResponseEntity.ok(fertilizers);
    }
    
    @PostMapping
    public ResponseEntity<Fertilizer> createFertilizer(@Valid @RequestBody Fertilizer fertilizer) {
        Fertilizer savedFertilizer = fertilizerService.saveFertilizer(fertilizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFertilizer);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fertilizer> updateFertilizer(@PathVariable Long id, @Valid @RequestBody Fertilizer fertilizerDetails) {
        try {
            Fertilizer updatedFertilizer = fertilizerService.updateFertilizer(id, fertilizerDetails);
            return ResponseEntity.ok(updatedFertilizer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFertilizer(@PathVariable Long id) {
        try {
            fertilizerService.deleteFertilizer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
