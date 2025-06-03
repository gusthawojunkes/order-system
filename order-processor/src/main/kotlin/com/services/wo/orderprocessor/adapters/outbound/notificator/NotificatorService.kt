package com.services.wo.orderprocessor.adapters.outbound.notificator

import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.domain.ports.NotificatorClientPort
import com.services.wo.orderprocessor.infrastructure.feign.NotificatorClient
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class NotificatorService(
    private val client: NotificatorClient
) : NotificatorClientPort {

    private val logger = LoggerFactory.getLogger(NotificatorService::class.java)

    @Async
    override fun sendNotification(order: Order) {
        logger.info("Sending notification to customer with email: ${order.customer.email}")
        client.sendEmail(order)
    }

}