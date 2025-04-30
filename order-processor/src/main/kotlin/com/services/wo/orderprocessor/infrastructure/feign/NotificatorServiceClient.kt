package com.services.wo.orderprocessor.infrastructure.feign

import com.services.wo.orderprocessor.domain.models.Order
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "notificator-service", url = "\${notificator-url}")
interface NotificatorServiceClient {
    @PostMapping("/v1/notify/email")
    fun sendEmail(@RequestBody order: Order)
}