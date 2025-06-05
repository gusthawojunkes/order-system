package com.services.wo.orderwriter.infrastructure.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory

class OrderProducerConfigTest {

    private val kafkaServer = "localhost:9092"
    private val orderProducerConfig = OrderProducerConfig().apply {
        val field = this::class.java.getDeclaredField("kafkaServer")
        field.isAccessible = true
        field.set(this, "localhost:9092") // Correctly set the value on the instance
    }

    @Test
    fun `creates producer factory with correct properties`() {
        val producerFactory = orderProducerConfig.producerFactory()

        assertNotNull(producerFactory)
        val configs = (producerFactory as DefaultKafkaProducerFactory<*, *>).configurationProperties
        assertEquals(kafkaServer, configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG])
        assertEquals(StringSerializer::class.java, configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG])
        assertEquals(StringSerializer::class.java, configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG])
    }

    @Test
    fun `creates kafka template with producer factory`() {
        val producerFactory = mock(ProducerFactory::class.java) as ProducerFactory<String, String>
        val configSpy = spy(orderProducerConfig)
        doReturn(producerFactory).`when`(configSpy).producerFactory()

        val kafkaTemplate = configSpy.kafkaTemplate()

        assertNotNull(kafkaTemplate)
        assertEquals(producerFactory, kafkaTemplate.producerFactory)
    }
}