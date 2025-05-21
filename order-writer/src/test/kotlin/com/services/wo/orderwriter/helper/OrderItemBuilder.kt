package com.services.wo.orderwriter.helper

import com.services.wo.orderwriter.domain.models.OrderItem
import java.math.BigDecimal
import java.util.UUID

class OrderItemBuilder {
    private var id: String = "default-item-id"
    private var name: String = "default-item-name"
    private var price: BigDecimal = BigDecimal.ZERO
    private var quantity: BigDecimal = BigDecimal.ZERO
    private var currency: String = "R$"

    fun withId(id: String) = apply { this.id = id }
    fun withName(name: String) = apply { this.name = name }
    fun withPrice(price: BigDecimal) = apply { this.price = price }
    fun withQuantity(quantity: BigDecimal) = apply { this.quantity = quantity }
    fun withCurrency(currency: String) = apply { this.currency = currency }

    fun default() = apply {
        this.id = UUID.randomUUID().toString()
        this.name = "default-item-name"
        this.price = BigDecimal("10.00")
        this.quantity = BigDecimal("1")
        this.currency = "BRL"
    }

    fun build(): OrderItem {
        return OrderItem(
            id = id,
            name = name,
            price = price,
            quantity = quantity,
            currency = currency
        )
    }
}