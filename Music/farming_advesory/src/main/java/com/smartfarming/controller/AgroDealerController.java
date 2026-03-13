package com.smartfarming.controller;

import com.smartfarming.entity.AgroDealer;
import com.smartfarming.service.AgroDealerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agro-dealers")
@CrossOrigin(origins = "*")
public class AgroDealerController {
    
    @Autowired
    private AgroDealerService agroDealerService;
    
    @GetMapping
    public ResponseEntity<List<AgroDealer>> getAllAgroDealers() {
        List<AgroDealer> agroDealers = agroDealerService.getAllAgroDealers();
        return ResponseEntity.ok(agroDealers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AgroDealer> getAgroDealerById(@PathVariable Long id) {
        return agroDealerService.getAgroDealerById(id)
            .map(agroDealer -> ResponseEntity.ok(agroDealer))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/phone/{phone}")
    public ResponseEntity<AgroDealer> getAgroDealerByPhone(@PathVariable String phone) {
        return agroDealerService.getAgroDealerByPhone(phone)
            .map(agroDealer -> ResponseEntity.ok(agroDealer))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<List<AgroDealer>> getAgroDealersByLocation(@PathVariable String location) {
        List<AgroDealer> agroDealers = agroDealerService.getAgroDealersByLocation(location);
        return ResponseEntity.ok(agroDealers);
    }
    
    @PostMapping
    public ResponseEntity<AgroDealer> createAgroDealer(@Valid @RequestBody AgroDealer agroDealer) {
        try {
            AgroDealer savedAgroDealer = agroDealerService.saveAgroDealer(agroDealer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAgroDealer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AgroDealer> updateAgroDealer(@PathVariable Long id, @Valid @RequestBody AgroDealer agroDealerDetails) {
        try {
            AgroDealer updatedAgroDealer = agroDealerService.updateAgroDealer(id, agroDealerDetails);
            return ResponseEntity.ok(updatedAgroDealer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgroDealer(@PathVariable Long id) {
        try {
            agroDealerService.deleteAgroDealer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
