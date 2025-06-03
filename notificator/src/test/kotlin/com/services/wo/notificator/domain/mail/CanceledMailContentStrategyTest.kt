package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CanceledMailContentStrategyTest {

    @Test
    fun subjectReturnsCorrectMessage() {
        val customer = CustomerBuilder().withName("John Doe").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = CanceledMailContentStrategy(order)

        val expectedSubject = "❌ Seu pedido #1 foi cancelado"
        assertEquals(expectedSubject, strategy.subject())
    }

    @Test
    fun bodyReturnsFormattedHtmlWithOrderDetails() {
        val customer = CustomerBuilder().withName("John Doe").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = CanceledMailContentStrategy(order)
        val currentYear = LocalDate.now().year

        val body = strategy.body()

        assert(body.contains("<h2 style=\"color: #2c3e50;\">Pedido Cancelado!</h2>"))
        assert(body.contains("<p>Olá <strong>John Doe</strong>,</p>"))
        assert(body.contains("<p>Seu pedido <strong>#1</strong> foi cancelado.</p>"))
        assert(body.contains("&copy; $currentYear WOIT Solutions"))
    }

}
