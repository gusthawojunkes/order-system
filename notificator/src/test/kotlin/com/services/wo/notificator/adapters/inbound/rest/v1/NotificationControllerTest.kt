package com.services.wo.notificator.adapters.inbound.rest.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.notificator.application.MailService
import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(NotificationController::class)
class NotificationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var service: MailService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return 200 when email notification is successfully sent`() {
        val order = OrderBuilder()
            .withId("123")
            .withPrice("100.00")
            .withQuantity("2")
            .withCurrency("BRL")
            .withStatus(OrderStatus.IN_PROGRESS)
            .build()

        mockMvc.perform(
            post("/v1/notify/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order))
        ).andExpect(status().isOk)
    }
}