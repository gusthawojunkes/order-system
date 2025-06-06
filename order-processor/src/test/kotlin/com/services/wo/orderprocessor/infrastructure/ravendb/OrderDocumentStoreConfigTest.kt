package com.services.wo.orderprocessor.infrastructure.ravendb

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

class OrderDocumentStoreConfigTest {

    @Test
    fun documentStore_initializesWithCorrectUrlAndDatabase() {
        val config = OrderDocumentStoreConfig()
        ReflectionTestUtils.setField(config, "url", "http://localhost:8080")
        ReflectionTestUtils.setField(config, "database", "testDatabase")

        val documentStore = config.documentStore()

        assertEquals("http://localhost:8080", documentStore.urls[0])
        assertEquals("testDatabase", documentStore.database)
    }

    @Test
    fun documentStore_configuresEntityMapperForEnums() {
        val config = OrderDocumentStoreConfig()
        ReflectionTestUtils.setField(config, "url", "http://localhost:8080")
        ReflectionTestUtils.setField(config, "database", "testDatabase")

        val documentStore = config.documentStore()

        val entityMapper = documentStore.conventions.entityMapper
        assertTrue(entityMapper.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING))
        assertTrue(entityMapper.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING))
    }
}