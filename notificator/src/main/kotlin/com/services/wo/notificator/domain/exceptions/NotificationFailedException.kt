package com.services.wo.notificator.domain.exceptions

class NotificationFailedException(message: String) : Exception(message) {
    constructor(message: String?, cause: Throwable) : this(message ?: "Notification failed") {
        initCause(cause)
    }
}