package com.smartfarming.controller;

import com.smartfarming.entity.Farmer;
import com.smartfarming.service.FarmerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin(origins = "*")
public class FarmerController {
    
    @Autowired
    private FarmerService farmerService;
    
    @GetMapping
    public ResponseEntity<List<Farmer>> getAllFarmers() {
        List<Farmer> farmers = farmerService.getAllFarmers();
        return ResponseEntity.ok(farmers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) {
        return farmerService.getFarmerById(id)
            .map(farmer -> ResponseEntity.ok(farmer))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Farmer> getFarmerByPhoneNumber(@PathVariable String phoneNumber) {
        return farmerService.getFarmerByPhoneNumber(phoneNumber)
            .map(farmer -> ResponseEntity.ok(farmer))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Farmer>> getFarmersByLocation(@PathVariable String location) {
        List<Farmer> farmers = farmerService.getFarmersByLocation(location);
        return ResponseEntity.ok(farmers);
    }
    
    @PostMapping
    public ResponseEntity<Farmer> createFarmer(@Valid @RequestBody Farmer farmer) {
        try {
            Farmer savedFarmer = farmerService.saveFarmer(farmer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFarmer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable Long id, @Valid @RequestBody Farmer farmerDetails) {
        try {
            Farmer updatedFarmer = farmerService.updateFarmer(id, farmerDetails);
            return ResponseEntity.ok(updatedFarmer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        try {
            farmerService.deleteFarmer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
