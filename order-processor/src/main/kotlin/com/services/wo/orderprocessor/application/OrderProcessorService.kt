package com.services.wo.orderprocessor.application

import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.domain.ports.NotificatorClientPort
import com.services.wo.orderprocessor.domain.ports.OrderProcessorPort
import com.services.wo.orderprocessor.domain.repository.OrderRepositoryPort
import com.services.wo.orderprocessor.infrastructure.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderProcessorService(
    private val repository: OrderRepositoryPort,
    private val notificatorClient: NotificatorClientPort
) : OrderProcessorPort {

    private val logger = LoggerFactory.getLogger(OrderProcessorService::class.java)

    override fun process(order: Order) {
        repository.save(order)
        logger.info("Sending notification to customer")
        notificatorClient.sendNotification(order)
    }

}