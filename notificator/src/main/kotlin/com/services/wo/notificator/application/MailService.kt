package com.services.wo.notificator.application

import com.services.wo.notificator.application.factory.MailContentFactory
import com.services.wo.notificator.domain.exceptions.NotificationFailedException
import com.services.wo.notificator.domain.ports.MailServicePort
import com.services.wo.notificator.domain.models.Order
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailService (
    private val sender: JavaMailSender
): MailServicePort {

    private val logger = LoggerFactory.getLogger(MailService::class.java)

    @Throws(NotificationFailedException::class)
    override fun send(order: Order) {
        try {
            val email = order.customer.email
            val message: MimeMessage = sender.createMimeMessage()
            val helper = MimeMessageHelper(message, true, "UTF-8")

            val content = MailContentFactory.from(order)

            helper.setTo(email)
            helper.setSubject(content.subject())
            helper.setText(content.body(), true)

            sender.send(message)
            logger.info("Message sent to ${order.customer.email}")
        } catch (e: Exception) {
            logger.error("Error creating mail content for order ${order.id}: ${e.message}")
            throw NotificationFailedException(e.message, e)
        }
    }
}