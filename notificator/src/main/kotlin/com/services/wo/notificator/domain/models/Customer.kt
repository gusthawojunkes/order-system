package com.services.wo.notificator.domain.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class Customer (
    @Schema(description = "Name of the customer")
    @NotBlank val name: String,

    @Schema(description = "Email address of the customer", example = "test@mail.com")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    @Min(6) val email: String
)
