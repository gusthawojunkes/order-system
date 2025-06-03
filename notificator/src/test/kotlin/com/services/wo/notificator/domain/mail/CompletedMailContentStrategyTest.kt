package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CompletedMailContentStrategyTest {

    @Test
    fun subjectReturnsCorrectMessage() {
        val customer = CustomerBuilder().withName("John Doe").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = CompletedMailContentStrategy(order)

        val expectedSubject = "✅ Você recebeu seu pedido #1!"
        assertEquals(expectedSubject, strategy.subject())
    }

    @Test
    fun bodyReturnsFormattedHtmlWithOrderDetails() {
        val customer = CustomerBuilder().withName("John Doe").build()
        val order = OrderBuilder().withId("1").withCustomer(customer).build()
        val strategy = CompletedMailContentStrategy(order)
        val currentYear = LocalDate.now().year

        val body = strategy.body()

        assert(body.contains("<h2 style=\"color: #2c3e50;\">Pedido entregue!</h2>"))
        assert(body.contains("<p>Olá <strong>Jane Doe</strong>,</p>"))
        assert(body.contains("<p>Seu pedido <strong>#1</strong> foi entregue com sucesso.</p>"))
        assert(body.contains("&copy; $currentYear WOIT Solutions"))
    }

}