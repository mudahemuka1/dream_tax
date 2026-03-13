package com.smartfarming.service;

import com.smartfarming.entity.Farmer;
import com.smartfarming.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerService {
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }
    
    public Optional<Farmer> getFarmerById(Long id) {
        return farmerRepository.findById(id);
    }
    
    public Optional<Farmer> getFarmerByPhoneNumber(String phoneNumber) {
        return farmerRepository.findByPhoneNumber(phoneNumber);
    }
    
    public Farmer saveFarmer(Farmer farmer) {
        if (farmerRepository.existsByPhoneNumber(farmer.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists: " + farmer.getPhoneNumber());
        }
        return farmerRepository.save(farmer);
    }
    
    public Farmer updateFarmer(Long id, Farmer farmerDetails) {
        return farmerRepository.findById(id)
            .map(farmer -> {
                farmer.setName(farmerDetails.getName());
                farmer.setLocation(farmerDetails.getLocation());
                
                if (!farmer.getPhoneNumber().equals(farmerDetails.getPhoneNumber()) &&
                    farmerRepository.existsByPhoneNumber(farmerDetails.getPhoneNumber())) {
                    throw new RuntimeException("Phone number already exists: " + farmerDetails.getPhoneNumber());
                }
                farmer.setPhoneNumber(farmerDetails.getPhoneNumber());
                return farmerRepository.save(farmer);
            })
            .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + id));
    }
    
    public void deleteFarmer(Long id) {
        if (!farmerRepository.existsById(id)) {
            throw new RuntimeException("Farmer not found with id: " + id);
        }
        farmerRepository.deleteById(id);
    }
    
    public List<Farmer> getFarmersByLocation(String location) {
        return farmerRepository.findByLocation(location);
    }
}
