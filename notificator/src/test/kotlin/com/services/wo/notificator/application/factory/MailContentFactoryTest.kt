package com.services.wo.notificator.application.factory

import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.notificator.domain.exceptions.UnsupportedMailContentException
import com.services.wo.notificator.domain.mail.*
import com.services.wo.notificator.domain.ports.MailContentStrategy
import com.services.wo.notificator.helper.OrderBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource


class MailContentFactoryTest {

    @ParameterizedTest
    @EnumSource(value = OrderStatus::class, names = ["RECEIVED", "IN_PROGRESS", "ON_THE_WAY", "COMPLETED", "CANCELED"])
    fun `should instance the correct MailContentStrategy`(status: OrderStatus) {
        val order = OrderBuilder().withStatus(status).build()

        val content: MailContentStrategy = MailContentFactory.from(order)

        assertNotNull(content, "Mail content should not be null")
        val expectedClass = when (status) {
            OrderStatus.RECEIVED -> ReceivedMailContentStrategy::class.java
            OrderStatus.IN_PROGRESS -> InProgressMailContentStrategy::class.java
            OrderStatus.ON_THE_WAY -> OnTheWayMailContentStrategy::class.java
            OrderStatus.COMPLETED -> CompletedMailContentStrategy::class.java
            OrderStatus.CANCELED -> CanceledMailContentStrategy::class.java
            else -> throw IllegalArgumentException("Unsupported status for this test")
        }
        assertEquals(expectedClass, content::class.java, "Mail content strategy should match the expected class")
    }

    @Test
    fun `should throw UnsupportedOperationException for PENDING status`() {
        val order = OrderBuilder().withStatus(OrderStatus.PENDING).build()

        val exception = assertThrows<UnsupportedMailContentException> {
            MailContentFactory.from(order)
        }

        assertEquals("Pending orders do not have a mail content strategy", exception.message)
    }

}