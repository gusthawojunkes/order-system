package com.services.wo.notificator

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableAsync

@OpenAPIDefinition(
	info = Info(
		title = "Notificator API",
		version = "1.0",
		description = "API for releasing notifications from the Order System Application",
		contact = Contact(name = "WOIT Solutions", email = "woitsolutions.co@gmail.com")
	)
)
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
class NotificatorApplication

fun main(args: Array<String>) {
	runApplication<NotificatorApplication>(*args)
}
