package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReceivedMailContentStrategyTest {

    @Test
    fun subjectReturnsCorrectMessage() {
        val customer = CustomerBuilder().withName("Charlie").build()
        val order = OrderBuilder().withId("1").withCurrency("USD").withQuantity("2").withPrice("100").withCustomer(customer).build()
        val strategy = ReceivedMailContentStrategy(order)

        val expectedSubject = "📦 Pedido #1 recebido com sucesso!"
        assertEquals(expectedSubject, strategy.subject())
    }

    @Test
    fun bodyReturnsFormattedHtmlWithOrderDetails() {
        val customer = CustomerBuilder().withName("Charlie").build()
        val order = OrderBuilder().withId("1").withCurrency("USD").withQuantity("2").withPrice("100").withCustomer(customer).build()
        val strategy = ReceivedMailContentStrategy(order)
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val currentYear = LocalDate.now().year

        val body = strategy.body()

        assert(body.contains("<h2 style=\"color: #2c3e50;\">Pedido Recebido!</h2>"))
        assert(body.contains("<p>Olá <strong>Charlie</strong>,</p>"))
        assert(body.contains("<p>Recebemos seu pedido <strong>#1</strong> com sucesso em <strong>$currentDate</strong>.</p>"))
        assert(body.contains("<li><strong>Valor Total:</strong> USD100.00</li>"))
        assert(body.contains("<li><strong>Quantidade:</strong> 2</li>"))
        assert(body.contains("&copy; $currentYear WOIT Solutions"))
    }

}