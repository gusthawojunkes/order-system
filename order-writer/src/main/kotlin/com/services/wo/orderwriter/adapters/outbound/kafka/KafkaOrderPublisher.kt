package com.services.wo.orderwriter.adapters.outbound.kafka

import com.services.wo.orderwriter.domain.ports.kakfa.PublishOrderPort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class KafkaOrderPublisher (
    private val kafkaTemplate: KafkaTemplate<String, String>
) : PublishOrderPort {

    override fun publish(order: String) {
        kafkaTemplate.send("orders", order)
    }
}