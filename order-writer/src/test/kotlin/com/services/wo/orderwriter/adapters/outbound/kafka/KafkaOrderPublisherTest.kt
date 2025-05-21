package com.services.wo.orderwriter.adapters.outbound.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.orderwriter.domain.models.Order
import com.services.wo.orderwriter.helper.OrderBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.util.ReflectionTestUtils
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class KafkaOrderPublisherTest {

    @Mock
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    private lateinit var kafkaOrderPublisher: KafkaOrderPublisher

    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    @Captor
    private lateinit var topicCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var keyCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var messageCaptor: ArgumentCaptor<String>

    private val topicName = "test-topic"

    @BeforeEach
    fun setup() {
        kafkaOrderPublisher = KafkaOrderPublisher(kafkaTemplate)
        ReflectionTestUtils.setField(kafkaOrderPublisher, "topicName", topicName)
    }

    @Test
    fun `should publish order message to kafka topic`() {
        val order = OrderBuilder()
            .default()
            .withPrice("100.00")
            .withStatus(OrderStatus.PENDING)
            .build()

        kafkaOrderPublisher.publish(order)

        verify(kafkaTemplate).send(
            topicCaptor.capture(),
            keyCaptor.capture(),
            messageCaptor.capture()
        )

        assertEquals(topicName, topicCaptor.value)
        assertNotNull(UUID.fromString(keyCaptor.value))

        val publishedOrder = this.objectMapper.readValue(messageCaptor.value, Order::class.java)
        
        assertEquals(order.id, publishedOrder.id)
        assertEquals(order.price, publishedOrder.price)
        assertEquals(order.status, publishedOrder.status)
    }

    @Test
    fun `should generate unique message id for each publish`() {
        val order = OrderBuilder().default().build()

        kafkaOrderPublisher.publish(order)
        kafkaOrderPublisher.publish(order)

        verify(kafkaTemplate, times(2)).send(
            topicCaptor.capture(),
            keyCaptor.capture(),
            messageCaptor.capture()
        )

        val messageIds = keyCaptor.allValues
        assertEquals(2, messageIds.size)
        assertEquals(2, messageIds.toSet().size)
    }

    @Test
    fun `should convert order to valid json`() {
        val order = OrderBuilder().default().build()

        kafkaOrderPublisher.publish(order)

        verify(kafkaTemplate).send(
            topicCaptor.capture(),
            keyCaptor.capture(),
            messageCaptor.capture()
        )

        val json = messageCaptor.value

        val deserializedOrder = this.objectMapper.readValue(json, Order::class.java)
        assertEquals(order, deserializedOrder)
    }
}