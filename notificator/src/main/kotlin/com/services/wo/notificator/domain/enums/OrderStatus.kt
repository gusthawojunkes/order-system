package com.services.wo.notificator.domain.enums

enum class OrderStatus (val description: String) {
    RECEIVED("Recebido"),
    PENDING("Pendente"),
    IN_PROGRESS("Em Andamento"),
    ON_THE_WAY("À caminho"),
    COMPLETED("Concluído"),
    CANCELED("Cancelado")
}