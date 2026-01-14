package com.github.iipanda

import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.CommandUtil
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.console.ConsoleSender
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.SoundUtil

class ReplyCommand(private val replyManager: ReplyManager) :
    CommandBase("reply", "Reply to your last private message") {
    init {
        setAllowsExtraArguments(true)
    }
    
    private val messageArgument = withListRequiredArg("message", "The message to send", ArgTypes.STRING)
    
    override fun executeSync(context: CommandContext) {
        val sender = context.sender()
        
        val receiverUuid = replyManager.getLastMessagedPlayer(sender.uuid)
            ?: return context.sendMessage(Message.raw("You have no one to reply to").color("#ff0055"))
        
        val receiver = Universe.get().getPlayer(receiverUuid)
            ?: return context.sendMessage(Message.raw("The player you're replying to is offline").color("#ff0055"))
        
        val msg = CommandUtil.stripCommandName(context.inputString)
        
        val senderName = when (sender) {
            is Player -> sender.displayName
            is ConsoleSender -> "Console"
            else -> sender.javaClass.simpleName
        }
        
        val message = buildMessage(senderName, receiver.username, msg)
        
        sender.sendMessage(message)
        
        val soundEventIndex = SoundEvent.getAssetMap().getIndex("Message_Received")
        receiver.sendMessage(message)
        SoundUtil.playSoundEvent2dToPlayer(receiver, soundEventIndex, SoundCategory.UI)
    }
}