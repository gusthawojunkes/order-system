package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InProgressMailContentStrategyTest {

    @Test
    fun subjectReturnsCorrectMessage() {
        val customer = CustomerBuilder().withName("Alice").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = InProgressMailContentStrategy(order)

        val expectedSubject = "🥄 Seu pedido #1 está sendo preparado pelo restaurante!"
        assertEquals(expectedSubject, strategy.subject())
    }

    @Test
    fun bodyReturnsFormattedHtmlWithOrderDetails() {
        val customer = CustomerBuilder().withName("Alice").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = InProgressMailContentStrategy(order)
        val currentYear = LocalDate.now().year

        val body = strategy.body()

        assert(body.contains("<h2 style=\"color: #2c3e50;\">Pedido em andamento!</h2>"))
        assert(body.contains("<p>Olá <strong>Alice</strong>,</p>"))
        assert(body.contains("<p>Seu pedido <strong>#1</strong> começou a ser preparado pelo restaurante.</p>"))
        assert(body.contains("&copy; $currentYear WOIT Solutions"))
    }

}