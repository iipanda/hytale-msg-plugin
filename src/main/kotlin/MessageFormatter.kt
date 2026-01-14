package com.github.iipanda

import com.hypixel.hytale.protocol.FormattedMessage
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandSender
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.util.Config

class MessageFormatter(private val config: Config<MsgPluginConfig>) {
    fun format(template: Message, values: Map<String, String>): Message {
        val formatted = template.formattedMessage.clone()
        applyPlaceholders(formatted, values)
        return Message(formatted)
    }
    
    fun formatPrivateMessage(sender: CommandSender, receiver: PlayerRef, content: String): Message {
        val cfg = config.get()
        val template = cfg.messageFormat
        
        return format(
            template,
            mapOf(
                "sender" to sender.displayName,
                "receiver" to receiver.username,
                "message" to content,
            ),
        )
    }
    
    private fun applyPlaceholders(message: FormattedMessage, values: Map<String, String>) {
        message.rawText = message.rawText?.let { replacePlaceholders(it, values) }
        message.children?.forEach { applyPlaceholders(it, values) }
    }
    
    private fun replacePlaceholders(text: String, values: Map<String, String>): String =
        PLACEHOLDER_REGEX.replace(text) { match ->
            values[match.groupValues[1]] ?: match.value
        }
    
    private companion object {
        private val PLACEHOLDER_REGEX = Regex("\\{([a-zA-Z0-9_]+)}")
    }
}
