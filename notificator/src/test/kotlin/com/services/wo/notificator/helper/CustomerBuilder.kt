package com.services.wo.notificator.helper

import com.services.wo.notificator.domain.models.Customer

class CustomerBuilder {
    private var name: String = "John Doe"
    private var email: String = "customer@mail.com"

    fun withName(name: String) = apply { this.name = name }
    fun withEmail(email: String) = apply { this.email = email }

    fun build(): Customer {
        return Customer(
            name = name,
            email = email
        )
    }
}