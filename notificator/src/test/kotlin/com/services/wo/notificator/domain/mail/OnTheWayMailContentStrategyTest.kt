package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OnTheWayMailContentStrategyTest {

    @Test
    fun subjectReturnsCorrectMessage() {
        val customer = CustomerBuilder().withName("Bob").build()
        val order = OrderBuilder().withId("123").withCustomer(customer).build()
        val strategy = OnTheWayMailContentStrategy(order)

        val expectedSubject = "🛵 Seu pedido #123 está a caminho do seu endereço"
        assertEquals(expectedSubject, strategy.subject())
    }

    @Test
    fun bodyReturnsFormattedHtmlWithOrderDetails() {
        val customer = CustomerBuilder().withName("Bob").build()
        val order = OrderBuilder().withId("123").withCustomer(customer).build()
        val strategy = OnTheWayMailContentStrategy(order)
        val currentYear = LocalDate.now().year

        val body = strategy.body()

        assert(body.contains("<h2 style=\"color: #2c3e50;\">Pedido a caminho!</h2>"))
        assert(body.contains("<p>Olá <strong>Bob</strong>,</p>"))
        assert(body.contains("<p>Seu pedido <strong>#123</strong> está a caminho do seu endereço.</p>"))
        assert(body.contains("&copy; $currentYear WOIT Solutions"))
    }

}