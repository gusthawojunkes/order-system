package com.services.wo.notificator.domain.mail

import com.services.wo.notificator.domain.models.Order
import com.services.wo.notificator.domain.ports.MailContentStrategy
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReceivedMailContentStrategy (override val order: Order) : MailContentStrategy {

    override fun subject(): String = "📦 Pedido #${order.id} recebido com sucesso!"

    override fun body(): String {
        val companyName = "WOIT Solutions"
        val today = LocalDate.now()
        val formattedToday = today.format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
        return """
            <html>
              <body style="font-family: Arial, sans-serif;">
                <div style="max-width: 600px; margin: auto; background-color: #f9f9f9; padding: 20px;">
                  <h2 style="color: #2c3e50;">Pedido Recebido!</h2>
                  <p>Olá <strong>${order.customer.name}</strong>,</p>
                  <p>Recebemos seu pedido <strong>#${order.id}</strong> com sucesso em <strong>$formattedToday</strong>.</p>
                   <p>Detalhes do pedido:</p>
                    <ul>
                        <li><strong>Valor Total:</strong> ${order.currency}${"%.2f".format(order.price)}</li>
                        <li><strong>Quantidade:</strong> ${order.quantity}</li>
                    </ul>
                  <p>Obrigado por comprar com a gente! 💙</p>
                  <hr>
                  <p style="font-size: 12px; color: #999;">&copy; ${today.year} $companyName</p>
                </div>
              </body>
            </html>
        """.trimIndent()
    }

}