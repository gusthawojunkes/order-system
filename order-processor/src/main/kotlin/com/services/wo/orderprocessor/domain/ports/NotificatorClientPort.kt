package com.services.wo.orderprocessor.domain.ports

import com.services.wo.orderprocessor.domain.models.Order

interface NotificatorClientPort {

    fun sendNotification(order: Order)

}