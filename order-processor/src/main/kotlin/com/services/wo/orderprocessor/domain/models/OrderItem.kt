package com.services.wo.orderprocessor.domain.models

import java.math.BigDecimal

data class OrderItem(
    val id: String = "",
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val quantity: BigDecimal = BigDecimal.ZERO,
    val currency: String = "R$",
)
