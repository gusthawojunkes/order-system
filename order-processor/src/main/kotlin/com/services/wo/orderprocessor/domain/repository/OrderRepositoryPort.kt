package com.services.wo.orderprocessor.domain.repository

import com.services.wo.orderprocessor.domain.models.Order

interface OrderRepositoryPort {
    fun save(order: Order)
}