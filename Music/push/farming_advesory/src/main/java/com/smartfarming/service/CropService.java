package com.smartfarming.service;

import com.smartfarming.entity.Crop;
import com.smartfarming.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropService {
    
    @Autowired
    private CropRepository cropRepository;
    
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }
    
    public Optional<Crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }
    
    public Optional<Crop> getCropByName(String name) {
        return cropRepository.findByCropNameIgnoreCase(name);
    }
    
    public Crop saveCrop(Crop crop) {
        if (cropRepository.existsByCropNameIgnoreCase(crop.getCropName())) {
            throw new RuntimeException("Crop already exists: " + crop.getCropName());
        }
        return cropRepository.save(crop);
    }
    
    public Crop updateCrop(Long id, Crop cropDetails) {
        return cropRepository.findById(id)
            .map(crop -> {
                crop.setCropName(cropDetails.getCropName());
                crop.setIdealSoil(cropDetails.getIdealSoil());
                crop.setIdealSeason(cropDetails.getIdealSeason());
                return cropRepository.save(crop);
            })
            .orElseThrow(() -> new RuntimeException("Crop not found with id: " + id));
    }
    
    public void deleteCrop(Long id) {
        if (!cropRepository.existsById(id)) {
            throw new RuntimeException("Crop not found with id: " + id);
        }
        cropRepository.deleteById(id);
    }
    
    public List<Crop> getCropsBySoil(String soilType) {
        return cropRepository.findByIdealSoilIgnoreCase(soilType);
    }
    
    public List<Crop> getCropsBySeason(String season) {
        return cropRepository.findByIdealSeasonIgnoreCase(season);
    }
    
    public List<Crop> getCropsBySoilAndSeason(String soil, String season) {
        return cropRepository.findBySoilAndSeason(soil, season);
    }
    
    public List<Crop> getRecommendedCrops(String soilType, String season) {
        return cropRepository.findBySoilAndSeason(soilType, season);
    }
}
