package com.smartfarming.controller;

import com.smartfarming.model.Crop;
import com.smartfarming.repository.CropRepository;
import com.smartfarming.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Crop addCrop(@RequestBody Crop crop) {
        return cropRepository.save(crop);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<Crop>> getRecommendedCrops(
            @RequestParam String soil,
            @RequestParam String season) {
        return ResponseEntity.ok(recommendationService.recommendCrops(soil, season));
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seedCrops() {
        cropRepository.deleteAll();
        
        List<Crop> defaultCrops = new ArrayList<>();
        
        Crop t = new Crop();
        t.setName("Roma Tomatoes");
        t.setDescription("Commercial grade tomatoes with high resistance to blight. kwitabhwaho cyane harimo kuhira (irrigation). Gisarurwa mu minsi 90-120.");
        t.setSuitableSoilType("Loamy soil (ubutaka bwiza buvanze neza, butumisha amazi)");
        t.setSuitableSeason("Season B: Feb – June (Summer Season)");
        t.setGrowingDurationDays("90-120");
        defaultCrops.add(t);
        
        Crop p = new Crop();
        p.setName("Pavuro");
        p.setDescription("Pavuro (beans) ni igihingwa cyingenzi mu Rwanda gitanga proteine nyinshi. Gikura neza mu butaka bwa loam bufite ifumbire.");
        p.setSuitableSoilType("Loamy soil (ivangavanga: umusenyi + ibumba + ifumbire)");
        p.setSuitableSeason("Season A: Sept – Jan (Winter Season)");
        p.setGrowingDurationDays("95");
        defaultCrops.add(p);
        
        cropRepository.saveAll(defaultCrops);
        return ResponseEntity.ok("Database seeded successfully with Rwandan seasons!");
    }
}
