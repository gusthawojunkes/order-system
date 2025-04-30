package com.services.wo.orderwriter.domain.ports.service

import com.services.wo.orderwriter.domain.models.Order

interface OrderWriterServicePort {

    fun write(order: Order)

}