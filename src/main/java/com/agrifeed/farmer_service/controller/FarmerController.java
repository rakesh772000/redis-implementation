package com.agrifeed.farmer_service.controller;

import com.agrifeed.farmer_service.dto.FarmerRequest;
import com.agrifeed.farmer_service.dto.FarmerResponse;
import com.agrifeed.farmer_service.service.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private static final Logger log = LoggerFactory.getLogger(FarmerController.class);

    private final FarmerService farmerService;

//    public FarmerController(FarmerService farmerService) {
//        this.farmerService = farmerService;
//    }

    @PostMapping
    public FarmerResponse addFarmer(@Valid @RequestBody FarmerRequest request) {
        log.info("Creating farmer: {}", request);
        return farmerService.createFarmer(request);
    }

    @GetMapping
    public List<FarmerResponse> getAllFarmers() {
        return farmerService.getAllFarmers();
    }

    @GetMapping("/{id}")
    public FarmerResponse getFarmerById(@PathVariable Long id) {
        return farmerService.getFarmerById(id);
    }

    @PutMapping("/{id}")
    public FarmerResponse updateFarmer(@PathVariable Long id, @Valid @RequestBody FarmerRequest request) {
        return farmerService.updateFarmer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
    }
}