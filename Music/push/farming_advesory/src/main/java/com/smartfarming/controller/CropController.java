package com.smartfarming.controller;

import com.smartfarming.entity.Crop;
import com.smartfarming.service.CropService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin(origins = "*")
public class CropController {
    
    @Autowired
    private CropService cropService;
    
    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        List<Crop> crops = cropService.getAllCrops();
        return ResponseEntity.ok(crops);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        return cropService.getCropById(id)
            .map(crop -> ResponseEntity.ok(crop))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Crop> getCropByName(@PathVariable String name) {
        return cropService.getCropByName(name)
            .map(crop -> ResponseEntity.ok(crop))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/soil/{soilType}")
    public ResponseEntity<List<Crop>> getCropsBySoil(@PathVariable String soilType) {
        List<Crop> crops = cropService.getCropsBySoil(soilType);
        return ResponseEntity.ok(crops);
    }
    
    @GetMapping("/season/{season}")
    public ResponseEntity<List<Crop>> getCropsBySeason(@PathVariable String season) {
        List<Crop> crops = cropService.getCropsBySeason(season);
        return ResponseEntity.ok(crops);
    }
    
    @GetMapping("/recommend")
    public ResponseEntity<List<Crop>> getRecommendedCrops(
            @RequestParam String soil, 
            @RequestParam String season) {
        List<Crop> crops = cropService.getRecommendedCrops(soil, season);
        return ResponseEntity.ok(crops);
    }
    
    @PostMapping
    public ResponseEntity<Crop> createCrop(@Valid @RequestBody Crop crop) {
        try {
            Crop savedCrop = cropService.saveCrop(crop);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable Long id, @Valid @RequestBody Crop cropDetails) {
        try {
            Crop updatedCrop = cropService.updateCrop(id, cropDetails);
            return ResponseEntity.ok(updatedCrop);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        try {
            cropService.deleteCrop(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
