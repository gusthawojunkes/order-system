package com.services.wo.orderwriter.adapters.outbound.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.orderwriter.domain.models.Order
import com.services.wo.orderwriter.domain.ports.kakfa.OrderPublisherPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaOrderPublisher (
    private val kafkaTemplate: KafkaTemplate<String, String>
) : OrderPublisherPort {

    private val logger = LoggerFactory.getLogger(KafkaOrderPublisher::class.java)

    @Value("\${kafka.topic}")
    private lateinit var topicName: String

    override fun publish(order: Order) {
        val json: String = ObjectMapper().writeValueAsString(order)
        val messageId: String = UUID.randomUUID().toString()
        kafkaTemplate.send(topicName, messageId, json)
        logger.info("Message sent to Kafka topic '$topicName' with ID: $messageId")
    }

}