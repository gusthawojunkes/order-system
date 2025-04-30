package com.services.wo.orderprocessor.helper

import com.services.wo.orderprocessor.domain.models.OrderItem
import java.math.BigDecimal

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