package com.services.wo.notificator.domain.ports

import com.services.wo.notificator.domain.exceptions.NotificationFailedException
import com.services.wo.notificator.domain.models.Order

interface MailServicePort {

    @Throws(NotificationFailedException::class)
    fun send(order: Order)
}