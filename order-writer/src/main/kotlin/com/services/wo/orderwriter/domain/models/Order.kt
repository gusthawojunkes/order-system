package com.services.wo.orderwriter.domain.models

import com.services.wo.notificator.domain.enums.OrderStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class Order (
    @Schema(description = "Unique identifier for the order")
    @NotBlank val id: String,

    @Schema(description = "Price of the order")
    val price: BigDecimal,

    @Schema(description = "Quantity of items in the order")
    val quantity: BigDecimal,

    @Schema(description = "Currency of the order price")
    val currency: String,

    @Schema(description = "Status of the order")
    val status: OrderStatus,

    @Schema(description = "Customer details associated with the order")
    @NotNull val customer: Customer,

    @Schema(description = "List of items in the order")
    @NotEmpty val items: List<OrderItem>,
)
