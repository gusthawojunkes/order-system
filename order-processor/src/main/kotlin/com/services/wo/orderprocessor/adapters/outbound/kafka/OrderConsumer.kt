package com.services.wo.orderprocessor.adapters.outbound.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.services.wo.orderprocessor.application.OrderProcessorService
import com.services.wo.orderprocessor.domain.models.Order
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class OrderConsumer(
    private val service: OrderProcessorService
) {

    private val logger = LoggerFactory.getLogger(OrderConsumer::class.java)
    private val objectMapper: ObjectMapper = ObjectMapper()

    @KafkaListener(topics = ["\${kafka.topic}"], groupId = "\${kafka.groupId}")
    fun run(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        logger.info("Processing message: ${record.key()}")
        try {
            val order: Order = objectMapper.readValue(record.value(), Order::class.java)
            service.process(order)
            ack.acknowledge()
            logger.info("Message ${record.key()} processed successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("Error processing message: ${e.message}", e)
            throw e
        }
    }

}