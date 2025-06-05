package com.services.wo.orderwriter.application

import com.services.wo.orderwriter.adapters.outbound.cache.OrderCache
import com.services.wo.orderwriter.domain.ExistingOrderException
import com.services.wo.orderwriter.domain.ports.kakfa.OrderPublisherPort
import com.services.wo.orderwriter.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderWriterServiceTest {

    @Mock
    private lateinit var orderPublisher: OrderPublisherPort

    @Mock
    private lateinit var cache: OrderCache

    @InjectMocks
    private lateinit var service: OrderWriterService

    @Test
    fun `should publish an valid order without throwing any exception`() {
        val order = OrderBuilder().default().build()

        `when`(cache.exists(order.id)).thenReturn(false)

        doNothing().`when`(cache).storeOrderId(order.id)
        doNothing().`when`(orderPublisher).publish(order)

        assertDoesNotThrow { service.write(order) }
    }

    @Test
    fun `should throw ExistingOrderException when order already exists in cache`() {
        val order = OrderBuilder().default().build()

        `when`(cache.exists(order.id)).thenReturn(true)

        val exception = assertThrows<ExistingOrderException> { service.write(order) }
        assertEquals("Order with ID ${order.id} already exists", exception.message)
    }

}