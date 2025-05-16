package com.services.wo.notificator.adapters.inbound.rest.v1

import com.services.wo.notificator.application.MailService
import com.services.wo.notificator.domain.models.Order
import com.services.wo.notificator.domain.ports.MailServicePort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as OperationRequestBody

@RestController
@RequestMapping("/v1/notify")
class NotificationController (
    private val service: MailServicePort
) {

    @Async
    @Operation(
        summary = "Release an order notification",
        description = "Sends an email notification for the order to the customer",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Order published successfully",
            ),
        ],
        tags = ["Notification"],

    )
    @OperationRequestBody(description = "The order to notify via email", required = true, content = [
        Content(
            mediaType = "application/json",
            schema = Schema(implementation = Order::class)
        )
    ])
    @PostMapping("/email")
    fun sendEmail(@RequestBody order: Order) {
        service.send(order)
    }

}