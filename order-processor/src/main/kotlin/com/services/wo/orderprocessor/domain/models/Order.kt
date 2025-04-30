package com.services.wo.orderprocessor.domain.models

import com.services.wo.orderprocessor.domain.enums.OrderStatus
import java.math.BigDecimal

data class Order(
    val id: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val quantity: BigDecimal = BigDecimal.ZERO,
    val currency: String = "R$",
    val status: OrderStatus = OrderStatus.PENDING,
    val customer: Customer = Customer(),
    val items: List<OrderItem> = emptyList(),
)
