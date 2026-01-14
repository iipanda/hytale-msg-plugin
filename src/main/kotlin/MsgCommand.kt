package com.github.iipanda

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.console.ConsoleSender
import com.hypixel.hytale.server.core.entity.entities.Player

class MsgCommand : CommandBase("msg", "Private message the player") {
    init {
        addAliases("pm", "tell", "whisper")
    }
    
    private val playerArgument = withRequiredArg("player", "The player to send the message to", ArgTypes.PLAYER_REF)
    private val messageArgument = withListRequiredArg("message", "The message to send", ArgTypes.STRING)
    
    override fun executeSync(context: CommandContext) {
        val receiver = playerArgument.get(context)
        val msg = messageArgument.get(context).joinToString(" ")
        
        val sender = context.sender()
        val senderName = when (sender) {
            is Player -> sender.displayName
            is ConsoleSender -> "Console"
            else -> sender.javaClass.simpleName
        }
        
        val message = buildMessage(senderName, receiver.username, msg)
        
        sender.sendMessage(message)
        
        receiver.sendMessage(message)
    }
    
    private fun buildMessage(senderName: String, receiverName: String, content: String) =
        Message.join(
            Message.raw("[$senderName -> $receiverName]").color("#02b511"),
            Message.raw(" "),
            Message.raw(content)
        )
}