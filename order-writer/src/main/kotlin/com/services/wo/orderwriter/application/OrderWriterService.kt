package com.services.wo.orderwriter.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.orderwriter.domain.models.Order
import com.services.wo.orderwriter.domain.ports.service.OrderWriterServicePort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderWriterService (
    private val kafkaTemplate: KafkaTemplate<String, String>,
): OrderWriterServicePort {

    private val logger = LoggerFactory.getLogger(OrderWriterService::class.java)

    @Value("\${kafka.topic}")
    private lateinit var topicName: String

    override fun write(order: Order) {
        val json: String = ObjectMapper().writeValueAsString(order)
        val messageId: String = UUID.randomUUID().toString()
        kafkaTemplate.send(topicName, messageId, json)
        logger.info("Message sent to Kafka topic $topicName with ID: $messageId")
    }

}