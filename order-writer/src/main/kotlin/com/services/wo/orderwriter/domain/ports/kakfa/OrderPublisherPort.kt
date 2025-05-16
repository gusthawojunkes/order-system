package com.services.wo.orderwriter.domain.ports.kakfa

import com.services.wo.orderwriter.domain.models.Order

interface OrderPublisherPort {
    fun publish(order: Order)
}