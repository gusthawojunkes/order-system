package com.services.wo.orderprocessor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableFeignClients
@SpringBootApplication
class OrderProcessorApplication

fun main(args: Array<String>) {
	runApplication<OrderProcessorApplication>(*args)
}
