package com.services.wo.orderwriter.adapters.outbound.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class OrderCache(private val redisTemplate: StringRedisTemplate) {

    @Value("\${cache.ttl:3600}")
    private var ttl: Long = 3600

    fun exists(id: String): Boolean {
        return redisTemplate.hasKey(id)
    }

    fun storeOrderId(orderId: String) {
        redisTemplate.opsForValue().set(orderId, "exists", this.ttl, TimeUnit.SECONDS)
    }
}