package com.services.wo.notificator.application

import com.services.wo.notificator.application.factory.MailContentFactory
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

    override fun send(order: Order) {
        val message: MimeMessage = sender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
        val email = order.customer.email

        val content = MailContentFactory.from(order)

        helper.setTo(email)
        helper.setSubject(content.subject())
        helper.setText(content.body(), true)

        sender.send(message)
        logger.info("Message ${order.id} sent to ${order.customer.name}")
    }

}