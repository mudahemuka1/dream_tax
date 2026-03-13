package com.smartfarming.controller;

import com.smartfarming.entity.Agronomist;
import com.smartfarming.service.AgronomistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agronomists")
@CrossOrigin(origins = "*")
public class AgronomistController {

    @Autowired
    private AgronomistService agronomistService;

    // GET all agronomists
    @GetMapping
    public ResponseEntity<List<Agronomist>> getAllAgronomists() {
        List<Agronomist> agronomists = agronomistService.getAllAgronomists();
        return ResponseEntity.ok(agronomists);
    }

    // GET agronomist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Agronomist> getAgronomistById(@PathVariable("id") Long id) {
        return agronomistService.getAgronomistById(id)
                .map(agronomist -> ResponseEntity.ok(agronomist))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET agronomist by phone
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Agronomist> getAgronomistByPhone(@PathVariable("phone") String phone) {
        return agronomistService.getAgronomistByPhone(phone)
                .map(agronomist -> ResponseEntity.ok(agronomist))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE agronomist
    @PostMapping
    public ResponseEntity<Agronomist> createAgronomist(@Valid @RequestBody Agronomist agronomist) {
        try {
            Agronomist savedAgronomist = agronomistService.saveAgronomist(agronomist);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAgronomist);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // UPDATE agronomist
    @PutMapping("/{id}")
    public ResponseEntity<Agronomist> updateAgronomist(@PathVariable("id") Long id,
                                                       @Valid @RequestBody Agronomist agronomistDetails) {
        try {
            Agronomist updatedAgronomist = agronomistService.updateAgronomist(id, agronomistDetails);
            return ResponseEntity.ok(updatedAgronomist);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE agronomist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgronomist(@PathVariable("id") Long id) {
        try {
            agronomistService.deleteAgronomist(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}