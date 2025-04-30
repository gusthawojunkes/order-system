package com.services.wo.orderwriter.helper

import com.services.wo.orderwriter.domain.models.Customer

class CustomerBuilder {
    private var id: String = "default-customer-id"
    private var name: String = "default-customer-name"
    private var email: String = "default@example.com"
    private var phone: String = "000-000-0000"
    private var address: String = "default-address"

    fun withId(id: String) = apply { this.id = id }
    fun withName(name: String) = apply { this.name = name }
    fun withEmail(email: String) = apply { this.email = email }
    fun withPhone(phone: String) = apply { this.phone = phone }
    fun withAddress(address: String) = apply { this.address = address }

    fun build(): Customer {
        return Customer(
            id = id,
            name = name,
            email = email,
            phone = phone,
            address = address
        )
    }
}