package com.services.wo.notificator.application.factory

import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.notificator.domain.mail.*
import com.services.wo.notificator.domain.models.Order
import com.services.wo.notificator.domain.ports.MailContentStrategy

object MailContentFactory {

    fun from(order: Order): MailContentStrategy = when (order.status) {
        OrderStatus.RECEIVED -> ReceivedMailContentStrategy(order)
        OrderStatus.IN_PROGRESS -> InProgressMailContentStrategy(order)
        OrderStatus.ON_THE_WAY -> OnTheWayMailContentStrategy(order)
        OrderStatus.COMPLETED -> CompletedMailContentStrategy(order)
        OrderStatus.CANCELED -> CanceledMailContentStrategy(order)
        OrderStatus.PENDING -> throw UnsupportedOperationException("Pending orders do not have a mail content strategy")
    }

}