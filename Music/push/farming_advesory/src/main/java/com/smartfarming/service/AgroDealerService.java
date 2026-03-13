package com.smartfarming.service;

import com.smartfarming.entity.AgroDealer;
import com.smartfarming.repository.AgroDealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgroDealerService {
    
    @Autowired
    private AgroDealerRepository agroDealerRepository;
    
    public List<AgroDealer> getAllAgroDealers() {
        return agroDealerRepository.findAll();
    }
    
    public Optional<AgroDealer> getAgroDealerById(Long id) {
        return agroDealerRepository.findById(id);
    }
    
    public Optional<AgroDealer> getAgroDealerByPhone(String phone) {
        return agroDealerRepository.findByPhone(phone);
    }
    
    public AgroDealer saveAgroDealer(AgroDealer agroDealer) {
        if (agroDealerRepository.existsByPhone(agroDealer.getPhone())) {
            throw new RuntimeException("Phone number already exists: " + agroDealer.getPhone());
        }
        return agroDealerRepository.save(agroDealer);
    }
    
    public AgroDealer updateAgroDealer(Long id, AgroDealer agroDealerDetails) {
        return agroDealerRepository.findById(id)
            .map(agroDealer -> {
                agroDealer.setName(agroDealerDetails.getName());
                agroDealer.setShopLocation(agroDealerDetails.getShopLocation());
                
                if (!agroDealer.getPhone().equals(agroDealerDetails.getPhone()) &&
                    agroDealerRepository.existsByPhone(agroDealerDetails.getPhone())) {
                    throw new RuntimeException("Phone number already exists: " + agroDealerDetails.getPhone());
                }
                agroDealer.setPhone(agroDealerDetails.getPhone());
                return agroDealerRepository.save(agroDealer);
            })
            .orElseThrow(() -> new RuntimeException("AgroDealer not found with id: " + id));
    }
    
    public void deleteAgroDealer(Long id) {
        if (!agroDealerRepository.existsById(id)) {
            throw new RuntimeException("AgroDealer not found with id: " + id);
        }
        agroDealerRepository.deleteById(id);
    }
    
    public List<AgroDealer> getAgroDealersByLocation(String location) {
        return agroDealerRepository.findByShopLocationContaining(location);
    }
}
