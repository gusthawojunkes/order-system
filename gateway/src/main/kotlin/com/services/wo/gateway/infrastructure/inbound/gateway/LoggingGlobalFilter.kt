package com.services.wo.gateway.infrastructure.inbound.gateway

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class LoggingGlobalFilter : GlobalFilter, Ordered {

    private val logger: Logger = LoggerFactory.getLogger(LoggingGlobalFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val startTime = System.currentTimeMillis()

        val address = request.remoteAddress?.address?.hostAddress ?: "unknown"
        logger.info("🔵 [{}] Incoming request: {} {}", address, request.method, request.uri)

        return chain.filter(exchange).then(
            Mono.fromRunnable {
                val duration = System.currentTimeMillis() - startTime
                val response = exchange.response
                logger.info("🟢 Response: {} - Duration: {}ms", response.statusCode, duration)
            }
        )
    }

    override fun getOrder(): Int = -1

}