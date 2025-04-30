package com.services.wo.orderprocessor.domain.enums

enum class OrderStatus(val description: String) {
    RECEIVED("Recebido"),
    PENDING("Pendente"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluído"),
    CANCELED("Cancelado")
}