package com.services.wo.orderwriter.adapters.inbound.rest.v1

import com.services.wo.orderwriter.application.OrderWriterService
import com.services.wo.orderwriter.domain.models.Order
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as OperationRequestBody

@RestController
@RequestMapping("/v1/order")
class OrderController(
    private val service: OrderWriterService
) {

    @Operation(
        summary = "Publish an order to the order topic",
        description = "Registers an order in the system",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Order published successfully",
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid order data",
            )
        ],
        tags = ["Order"],
    )
    @OperationRequestBody(description = "The order to be published", required = true, content = [
        Content(
            mediaType = "application/json",
            schema = Schema(implementation = Order::class)
        )
    ])
    @PostMapping
    fun register(@RequestBody order: Order): ResponseEntity<Void> {
        service.write(order).let {
            return ResponseEntity.ok().build()
        }
    }

}