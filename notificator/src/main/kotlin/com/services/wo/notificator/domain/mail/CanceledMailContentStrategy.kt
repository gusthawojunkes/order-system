package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.domain.models.Order
import com.services.wo.notificator.domain.ports.MailContentStrategy
import java.time.LocalDate

class CanceledMailContentStrategy (override val order: Order) : MailContentStrategy {
    override fun subject(): String = "❌ Seu pedido #${order.id} foi cancelado"

    override fun body(): String {
        val companyName = "WOIT Solutions"
        val today = LocalDate.now()
        return """
            <html>
              <body style="font-family: Arial, sans-serif;">
                <div style="max-width: 600px; margin: auto; background-color: #f9f9f9; padding: 20px;">
                  <h2 style="color: #2c3e50;">Pedido Cancelado!</h2>
                  <p>Olá <strong>${order.customer.name}</strong>,</p>
                  <p>Seu pedido <strong>#${order.id}</strong> foi cancelado.</p>
                  <p>Se você tiver alguma dúvida, entre em contato com nosso suporte.</p>
                  <p>Obrigado por comprar com a gente! 💙</p>
                  <hr>
                  <p style="font-size: 12px; color: #999;">&copy; ${today.year} $companyName</p>
                </div>
              </body>
            </html>
        """.trimIndent()
    }
}