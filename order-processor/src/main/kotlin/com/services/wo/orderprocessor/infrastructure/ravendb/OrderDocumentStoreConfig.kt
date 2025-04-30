package com.services.wo.orderprocessor.infrastructure.ravendb

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import net.ravendb.client.documents.DocumentStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderDocumentStoreConfig {

    @Value("\${ravendb.url}")
    private lateinit var url: String

    @Value("\${ravendb.database}")
    private lateinit var database: String

    @Bean
    fun documentStore(): DocumentStore {
        val store = DocumentStore(url, database)
        store.conventions.apply {
            isDisableTopologyUpdates = true // just for development!!!
            entityMapper.apply {
                configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
            }
        }
        store.initialize()
        return store
    }

}