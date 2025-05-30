package com.services.wo.orderprocessor.application

import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.domain.ports.NotificatorClientPort
import com.services.wo.orderprocessor.domain.ports.OrderProcessorPort
import com.services.wo.orderprocessor.domain.repository.OrderRepositoryPort
import org.springframework.stereotype.Service

@Service
class OrderProcessorService(
    private val repository: OrderRepositoryPort,
    private val notificatorClient: NotificatorClientPort
) : OrderProcessorPort {

    override fun process(order: Order) {
        order.startProcessing()
        repository.save(order)
        notificatorClient.sendNotification(order)
    }

}