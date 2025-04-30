package com.services.wo.notificator.domain.ports

import com.services.wo.notificator.domain.models.Order

interface MailContentStrategy {

    val order: Order

    fun subject(): String
    fun body(): String

}