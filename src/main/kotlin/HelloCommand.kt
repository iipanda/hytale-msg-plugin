package com.github.iipanda

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.entity.entities.Player

const val AQUA = "#55ffff"

class HelloCommand : CommandBase("hello", "Display hello message") {
    override fun executeSync(context: CommandContext) {
        val sender = context.sender()
        val player = sender as? Player
            ?: return sender.sendMessage(Message.raw("Only players are allowed to use this command"))
        
        val helloMessage = Message.join(
            Message.raw("Hello from "),
            Message.raw("Kotlin").color(AQUA).bold(true)
        )
        
        player.sendMessage(helloMessage)
    }
}