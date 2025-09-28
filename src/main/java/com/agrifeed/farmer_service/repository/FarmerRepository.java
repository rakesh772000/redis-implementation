package com.agrifeed.farmer_service.repository;

import com.agrifeed.farmer_service.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByEmail(String email);
}
