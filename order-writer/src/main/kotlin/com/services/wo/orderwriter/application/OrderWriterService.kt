package com.services.wo.orderwriter.application

import com.services.wo.orderwriter.adapters.outbound.cache.OrderCache
import com.services.wo.orderwriter.domain.ExistingOrderException
import com.services.wo.orderwriter.domain.models.Order
import com.services.wo.orderwriter.domain.ports.kakfa.OrderPublisherPort
import com.services.wo.orderwriter.domain.ports.service.OrderWriterServicePort
import org.springframework.stereotype.Service

@Service
class OrderWriterService (
    private val orderPublisher: OrderPublisherPort,
    private val cache: OrderCache
): OrderWriterServicePort {

    override fun write(order: Order) {
        if (cache.exists(order.id)) {
            throw ExistingOrderException("Order with ID ${order.id} already exists")
        }
        cache.storeOrderId(order.id)
        orderPublisher.publish(order)
    }

}