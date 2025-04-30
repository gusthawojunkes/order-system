package com.services.wo.orderprocessor.adapters.outbound.notificator

import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.domain.ports.NotificatorClientPort
import com.services.wo.orderprocessor.infrastructure.feign.NotificatorServiceClient
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class NotificatorClient(
    private val client: NotificatorServiceClient
) : NotificatorClientPort {

    @Async
    override fun sendNotification(order: Order) {
        client.sendEmail(order)
    }

}