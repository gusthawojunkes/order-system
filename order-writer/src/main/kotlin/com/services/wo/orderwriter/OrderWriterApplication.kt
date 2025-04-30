package com.services.wo.orderwriter

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Order Writer API",
        version = "1.0",
        description = "API for writing an order into the Order System Application",
        contact = Contact(name = "WOIT Solutions", email = "woitsolutions.co@gmail.com")
    )
)
@SpringBootApplication
class OrderWriterApplication

fun main(args: Array<String>) {
	runApplication<OrderWriterApplication>(*args)
}
