package com.services.wo.orderwriter.application

import com.services.wo.orderwriter.domain.ports.kakfa.OrderPublisherPort
import com.services.wo.orderwriter.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderWriterServiceTest {

    @Mock
    private lateinit var orderPublisher: OrderPublisherPort

    @InjectMocks
    private lateinit var service: OrderWriterService

    @Test
    fun `should publish an valid order without throwing any exception`() {
        val order = OrderBuilder().default().build()

        doNothing().`when`(orderPublisher).publish(order)

        assertDoesNotThrow { service.write(order) }
    }

}