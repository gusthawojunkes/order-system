package com.services.wo.orderprocessor.adapters.outbound.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.orderprocessor.application.OrderProcessorService
import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.helper.OrderBuilder
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.kafka.support.Acknowledgment

class OrderConsumerTest {

    private val service: OrderProcessorService = mock(OrderProcessorService::class.java)
    private val objectMapper: ObjectMapper = mock(ObjectMapper::class.java)
    private val consumer = OrderConsumer(service)

    @Test
    fun `should process message successfully`() {
        val record = ConsumerRecord("orders", 0, 0L, "key", """{"id":"1","price":100.0,"quantity":2.0}""")
        val ack = mock(Acknowledgment::class.java)
        val order = OrderBuilder()
            .withId("1")
            .withPrice("100.0")
            .withQuantity("2.0")
            .build()

        consumer.run(record, ack)

        verify(service).process(order)
        verify(ack).acknowledge()
    }

    @Test
    fun `should handle exception when processing wrong message`() {
        val record = ConsumerRecord("orders", 0, 0L, "key", """{"idx":"2","pricex":400.0,"quantityx":8.0}""")
        val ack = mock(Acknowledgment::class.java)

        val order = mock(Order::class.java)
        `when`(service.process(order)).thenThrow(RuntimeException("Invalid Order"))

        assertThrows<Exception> {
            consumer.run(record, ack)
        }

        verify(service, never()).process(order)
        verify(ack, never()).acknowledge()
    }

    @Test
    fun `should not acknowledge if processing fails`() {
        val record = ConsumerRecord("orders", 0, 0L, "key", """{"id":"1","price":100.0,"quantity":2.0}""")
        val ack = mock(Acknowledgment::class.java)
        val order = OrderBuilder()
            .withId("1")
            .withPrice("100.0")
            .withQuantity("2.0")
            .build()

        `when`(objectMapper.readValue(record.value(), Order::class.java)).thenReturn(order)
        doThrow(RuntimeException("Processing error")).`when`(service).process(order)

        try {
            consumer.run(record, ack)
        } catch (ignored: Exception) {}

        verify(ack, never()).acknowledge()
    }
}