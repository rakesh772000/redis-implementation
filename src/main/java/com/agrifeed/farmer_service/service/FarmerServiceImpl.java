package com.agrifeed.farmer_service.service;

import com.agrifeed.farmer_service.dto.FarmerRequest;
import com.agrifeed.farmer_service.dto.FarmerResponse;
import com.agrifeed.farmer_service.entity.Farmer;
import com.agrifeed.farmer_service.exception.FarmerNotFoundException;
import com.agrifeed.farmer_service.repository.FarmerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FarmerServiceImpl implements FarmerService {

    private static final Logger log = LoggerFactory.getLogger(FarmerServiceImpl.class);
    private final FarmerRepository farmerRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Transactional
    @Override
    @CachePut(value = "FARMER_CACHE", key = "#result.id")
    public FarmerResponse createFarmer(FarmerRequest request) {
        if (request.name() == null || request.name().isEmpty() ||
                request.email() == null || request.email().isEmpty() ||
                request.phone() == null || request.phone().isEmpty()) {
            throw new RuntimeException("All fields (name, email, phone) are required");
        }
        log.info("New farmer is entering into our database");
        Farmer farmer = new Farmer();
        farmer.setName(request.name());
        farmer.setEmail(request.email());
        farmer.setPhone(request.phone());
        Farmer saved = farmerRepository.save(farmer);
        log.info("Welcome to Agrifeed {}", request.name());
        return new FarmerResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone());
    }


    @Override
    public List<FarmerResponse> getAllFarmers() {
        log.info("Fetching all the farmers in Agrifeed");
        return farmerRepository.findAll()
                .stream()
                .map(f -> new FarmerResponse(f.getId(), f.getName(), f.getEmail(), f.getPhone()))
                .toList();
    }

    @Cacheable(value = "FARMER_CACHE", key = "#id")
    @Override
    public FarmerResponse getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new FarmerNotFoundException("Farmer not found with id " + id));
        return new FarmerResponse(farmer.getId(), farmer.getName(), farmer.getEmail(), farmer.getPhone());
    }

    @Transactional
    @CachePut(value = "FARMER_CACHE", key = "#id")
    @Override
    public FarmerResponse updateFarmer(Long id, FarmerRequest request) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new FarmerNotFoundException("Farmer not found with id " + id));
        farmer.setName(request.name());
        farmer.setEmail(request.email());
        farmer.setPhone(request.phone());
        Farmer updated = farmerRepository.save(farmer);
        return new FarmerResponse(updated.getId(), updated.getName(), updated.getEmail(), updated.getPhone());
    }

    @Transactional
    @CacheEvict(value = "FARMER_CACHE", key = "#id")
    @Override
    public void deleteFarmer(Long id) {
        if (!farmerRepository.existsById(id)) {
            throw new FarmerNotFoundException("Farmer not found with id " + id);
        }
        farmerRepository.deleteById(id);
    }
}
