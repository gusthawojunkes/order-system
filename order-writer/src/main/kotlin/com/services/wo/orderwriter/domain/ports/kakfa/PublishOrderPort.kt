package com.services.wo.orderwriter.domain.ports.kakfa

interface PublishOrderPort {
    fun publish(order: String)
}