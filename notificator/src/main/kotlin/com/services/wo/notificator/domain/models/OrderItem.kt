package com.services.wo.notificator.domain.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class OrderItem (

    @Schema(description = "Unique identifier for the order item")
    @NotBlank val id: String,

    @Schema(description = "Name of the order item")
    @NotBlank val name: String,

    @Schema(description = "Price of the order item")
    @NotNull val price: BigDecimal,

    @Schema(description = "Quantity of the order item")
    @NotNull val quantity: BigDecimal,

    @Schema(description = "Currency of the order item price")
    @NotBlank val currency: String,
)
