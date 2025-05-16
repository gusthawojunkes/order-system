package com.services.wo.orderwriter.application

import com.services.wo.orderwriter.domain.models.Order
import com.services.wo.orderwriter.domain.ports.kakfa.OrderPublisherPort
import com.services.wo.orderwriter.domain.ports.service.OrderWriterServicePort
import org.springframework.stereotype.Service

@Service
class OrderWriterService (
    private val orderPublisher: OrderPublisherPort
): OrderWriterServicePort {

    override fun write(order: Order) {
        orderPublisher.publish(order)
    }

}