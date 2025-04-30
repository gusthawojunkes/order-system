package com.services.wo.notificator.domain.ports

import com.services.wo.notificator.domain.models.Order

interface MailServicePort {
    fun send(order: Order)
}