package com.smartfarming.service;

import com.smartfarming.entity.Agronomist;
import com.smartfarming.repository.AgronomistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgronomistService {
    
    @Autowired
    private AgronomistRepository agronomistRepository;
    
    public List<Agronomist> getAllAgronomists() {
        return agronomistRepository.findAll();
    }
    
    public Optional<Agronomist> getAgronomistById(Long id) {
        return agronomistRepository.findById(id);
    }
    
    public Optional<Agronomist> getAgronomistByPhone(String phone) {
        return agronomistRepository.findByPhone(phone);
    }
    
    public Agronomist saveAgronomist(Agronomist agronomist) {
        if (agronomistRepository.existsByPhone(agronomist.getPhone())) {
            throw new RuntimeException("Phone number already exists: " + agronomist.getPhone());
        }
        return agronomistRepository.save(agronomist);
    }
    
    public Agronomist updateAgronomist(Long id, Agronomist agronomistDetails) {
        return agronomistRepository.findById(id)
            .map(agronomist -> {
                agronomist.setName(agronomistDetails.getName());
                agronomist.setQualification(agronomistDetails.getQualification());
                
                if (!agronomist.getPhone().equals(agronomistDetails.getPhone()) &&
                    agronomistRepository.existsByPhone(agronomistDetails.getPhone())) {
                    throw new RuntimeException("Phone number already exists: " + agronomistDetails.getPhone());
                }
                agronomist.setPhone(agronomistDetails.getPhone());
                return agronomistRepository.save(agronomist);
            })
            .orElseThrow(() -> new RuntimeException("Agronomist not found with id: " + id));
    }
    
    public void deleteAgronomist(Long id) {
        if (!agronomistRepository.existsById(id)) {
            throw new RuntimeException("Agronomist not found with id: " + id);
        }
        agronomistRepository.deleteById(id);
    }
}
