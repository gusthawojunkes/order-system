package com.services.wo.orderwriter.adapters.outbound.cache

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.TimeUnit

@ExtendWith(MockitoExtension::class)
class OrderCacheTest {

    @Mock
    private lateinit var redisTemplate: StringRedisTemplate

    @InjectMocks
    private lateinit var orderCache: OrderCache

    @Test
    fun `returns true when order ID exists in cache`() {
        `when`(redisTemplate.hasKey("order123")).thenReturn(true)

        val result = orderCache.exists("order123")

        assertTrue(result)
    }

    @Test
    fun `returns false when order ID does not exist in cache`() {
        `when`(redisTemplate.hasKey("order123")).thenReturn(false)

        val result = orderCache.exists("order123")

        assertFalse(result)
    }

    @Test
    fun `stores order ID in cache with correct TTL`() {
        val opsForValue: ValueOperations<String, String> = mock(ValueOperations::class.java as Class<ValueOperations<String, String>>)
        `when`(redisTemplate.opsForValue()).thenReturn(opsForValue)

        orderCache.storeOrderId("order123")

        verify(opsForValue).set("order123", "exists", 3600, TimeUnit.SECONDS)
    }
}