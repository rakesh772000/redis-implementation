package com.agrifeed.farmer_service.service;

import com.agrifeed.farmer_service.dto.FarmerRequest;
import com.agrifeed.farmer_service.dto.FarmerResponse;

import java.util.List;

public interface FarmerService {
    FarmerResponse createFarmer(FarmerRequest request);
    List<FarmerResponse> getAllFarmers();
    FarmerResponse getFarmerById(Long id);
    FarmerResponse updateFarmer(Long id, FarmerRequest request);
    void deleteFarmer(Long id);
}
