package com.github.iipanda

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase

class ReplyCommand : CommandBase("reply", "Reply to your last private message") {
    override fun executeSync(context: CommandContext) {
        context.sendMessage(Message.raw("Not implemented").color("#ff0055"))
    }
}