package com.services.wo.orderprocessor.infrastructure.repository

import com.services.wo.orderprocessor.helper.OrderBuilder
import net.ravendb.client.documents.DocumentStore
import net.ravendb.client.documents.session.DocumentSession
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderRepositoryTest {

    private val documentStore: DocumentStore = mock(DocumentStore::class.java)
    private val repository: OrderRepository = OrderRepository(documentStore)

    @Test
    fun `should save order successfully`() {
        val order = OrderBuilder().withId("1").withPrice("100.0").withQuantity("2.0").build()

        val mockSession = mock(DocumentSession::class.java)
        `when`(documentStore.openSession()).thenReturn(mockSession)

        assertDoesNotThrow {
            repository.save(order)
        }
    }

    @Test
    fun `should handle exception when saving order`() {
        val order = OrderBuilder().withId("1").withPrice("100.0").withQuantity("2.0").build()

        val mockSession = mock(DocumentSession::class.java)
        `when`(documentStore.openSession()).thenReturn(mockSession)
        `when`(mockSession.store(order)).thenThrow(RuntimeException("Database error"))

        assertThrows<RuntimeException> {
            repository.save(order)
        }
    }
}