package com.services.wo.orderprocessor.application

import com.services.wo.orderprocessor.domain.ports.NotificatorClientPort
import com.services.wo.orderprocessor.domain.repository.OrderRepositoryPort
import com.services.wo.orderprocessor.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderProcessorServiceTest {

    @Mock
    private lateinit var repository: OrderRepositoryPort

    @Mock
    private lateinit var notificatorClient: NotificatorClientPort

    @InjectMocks
    private lateinit var service: OrderProcessorService

    @Test
    fun `processes order and saves it to repository`() {
        val order = OrderBuilder().build()

        doNothing().`when`(repository).save(order)
        doNothing().`when`(notificatorClient).sendNotification(order)

        assertDoesNotThrow {
            service.process(order)
        }

        verify(repository, times(1)).save(order)
        verify(notificatorClient, times(1)).sendNotification(order)
    }

    @Test
    fun `sends notification after processing order`() {
        val order = OrderBuilder().build()

        service.process(order)

        verify(notificatorClient).sendNotification(order)
    }

}