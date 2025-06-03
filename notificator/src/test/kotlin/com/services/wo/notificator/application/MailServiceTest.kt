package com.services.wo.notificator.application

import com.services.wo.notificator.domain.enums.OrderStatus
import com.services.wo.notificator.domain.exceptions.NotificationFailedException
import com.services.wo.notificator.helper.CustomerBuilder
import com.services.wo.notificator.helper.OrderBuilder
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.javamail.JavaMailSender

@ExtendWith(MockitoExtension::class)
class MailServiceTest {

    @Mock
    private lateinit var sender: JavaMailSender

    @InjectMocks
    private lateinit var service: MailService

    @Test
    fun `MailService should send the email successfully`() {
        val customer = CustomerBuilder().withEmail("test@mail.com").build()
        val order = OrderBuilder().withStatus(OrderStatus.RECEIVED).withCustomer(customer).build()

        `when`(sender.createMimeMessage()).thenReturn(mock(MimeMessage::class.java))
        doNothing().`when`(sender).send(any(MimeMessage::class.java))

        service.send(order)

        verify(sender).send(any(MimeMessage::class.java))
    }

    @Test
    fun `MailService should throw an NotificationFailedException when order status is PENDING`() {
        val customer = CustomerBuilder().withEmail("test@mail.com").build()
        val order = OrderBuilder().withStatus(OrderStatus.PENDING).withCustomer(customer).build()

        `when`(sender.createMimeMessage()).thenReturn(mock(MimeMessage::class.java))

        assertThrows<NotificationFailedException> {
            service.send(order)
        }.also {
            assertEquals("Pending orders do not have a mail content strategy", it.message)
        }

        verify(sender, times(0)).send(any(MimeMessage::class.java))
    }

}