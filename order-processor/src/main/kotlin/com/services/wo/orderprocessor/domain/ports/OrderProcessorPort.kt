package com.services.wo.orderprocessor.domain.ports

import com.services.wo.orderprocessor.domain.models.Order

interface OrderProcessorPort {
    fun process(order: Order)
}