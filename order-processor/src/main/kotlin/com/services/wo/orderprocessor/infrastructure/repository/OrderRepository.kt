package com.services.wo.orderprocessor.infrastructure.repository

import com.services.wo.orderprocessor.domain.models.Order
import com.services.wo.orderprocessor.domain.repository.OrderRepositoryPort
import net.ravendb.client.documents.DocumentStore
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class OrderRepository (
    private val documentStore: DocumentStore
): OrderRepositoryPort {

    private val logger = LoggerFactory.getLogger(OrderRepository::class.java)

    override fun save(order: Order) {
        try {
            documentStore.openSession().use { session ->
                session.store(order)
                session.saveChanges()
                logger.info("Order saved successfully: ${order.id}")
            }
        } catch (e: Exception) {
            logger.error("Error saving order: ${e.message}", e)
            throw e
        }
    }
}