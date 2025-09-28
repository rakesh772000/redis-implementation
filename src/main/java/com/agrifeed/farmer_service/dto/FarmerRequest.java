package com.agrifeed.farmer_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FarmerRequest (
  @NotBlank(message = "Farmer name is required") String name,
    @Email(message = "Email is mandatory")  String email,
    String phone
){};
