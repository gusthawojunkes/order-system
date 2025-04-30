package com.services.wo.orderwriter.adapters.inbound.rest.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.orderwriter.application.OrderWriterService
import com.services.wo.orderwriter.helper.CustomerBuilder
import com.services.wo.orderwriter.helper.OrderBuilder
import com.services.wo.orderwriter.helper.OrderItemBuilder
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(OrderController::class)
@Import(OrderControllerTest.TestConfig::class)
class OrderControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var service: OrderWriterService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @TestConfiguration
    class TestConfig {
        @Bean
        fun orderWriterService(): OrderWriterService = mock(OrderWriterService::class.java)
    }

    @Test
    fun `should return 200 when order is successfully registered`() {

        val order = OrderBuilder()
            .withId("123")
            .withPrice("100.00")
            .withQuantity("2")
            .withCurrency("BRL")
            .withStatus(OrderStatus.PENDING)
            .withCustomer(CustomerBuilder().build())
            .withItems(listOf(OrderItemBuilder().build()))
            .build()

        doNothing().`when`(service).write(order)

        mockMvc.perform(
            post("/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order))
        ).andExpect(status().isOk)

        verify(service, times(1)).write(order)
    }

    @Test
    fun `should return 400 when request body is invalid`() {
        val invalidOrder = "{}"

        mockMvc.perform(
            post("/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidOrder)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 when order has missing required fields`() {
        val invalidOrder = """
            {
                "id": "123",
                "price": "100.00"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidOrder)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 when order has invalid field values`() {
        val invalidOrder = """
            {
                "id": "123",
                "price": "-100.00",
                "quantity": "-2",
                "currency": "INVALID",
                "status": "INVALID_STATUS",
                "customer": null,
                "items": []
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidOrder)
        ).andExpect(status().isBadRequest)
    }

}