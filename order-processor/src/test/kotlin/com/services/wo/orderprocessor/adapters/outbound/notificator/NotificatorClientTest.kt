package com.services.wo.orderprocessor.adapters.outbound.notificator

import com.services.wo.orderprocessor.helper.OrderBuilder
import com.services.wo.orderprocessor.infrastructure.feign.NotificatorClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class NotificatorClientTest {

    @Mock
    private lateinit var feign: NotificatorClient

    @InjectMocks
    private lateinit var service: NotificatorService

    @Test
    fun `should send notification successfully`() {
        val order = OrderBuilder().build()

        doNothing().`when`(feign).sendEmail(order)

        assertDoesNotThrow { service.sendNotification(order) }
    }

}