package com.services.wo.notificator.helper

import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.notificator.domain.models.Customer
import com.services.wo.notificator.domain.models.Order
import com.services.wo.notificator.domain.models.OrderItem
import java.math.BigDecimal

class OrderBuilder {
    private var id: String = "default-id"
    private var price: BigDecimal = BigDecimal.ZERO
    private var quantity: BigDecimal = BigDecimal.ZERO
    private var currency: String = "R$"
    private var status: OrderStatus = OrderStatus.PENDING
    private var customer: Customer = Customer("", "default-customer", "", "000-000-0000", "default-address")
    private var items: List<OrderItem> = emptyList()

    fun withId(id: String) = apply { this.id = id }
    fun withPrice(price: String) = apply { this.price = BigDecimal(price) }
    fun withQuantity(quantity: String) = apply { this.quantity = BigDecimal(quantity) }
    fun withCurrency(currency: String) = apply { this.currency = currency }
    fun withStatus(status: OrderStatus) = apply { this.status = status }
    fun withCustomer(customer: Customer) = apply { this.customer = customer }
    fun withItems(items: List<OrderItem>) = apply { this.items = items }

    fun build(): Order {
        return Order(
            id = id,
            price = price,
            quantity = quantity,
            currency = currency,
            status = status,
            customer = customer,
            items = items
        )
    }
}