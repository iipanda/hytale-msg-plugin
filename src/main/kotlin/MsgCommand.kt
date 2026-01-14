package com.github.iipanda

import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.CommandUtil
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.console.ConsoleSender
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.world.SoundUtil

class MsgCommand(private val replyManager: ReplyManager) : CommandBase("msg", "Private message the player") {
    init {
        addAliases("pm", "tell", "whisper")
        setAllowsExtraArguments(true)
    }
    
    private val playerArgument = withRequiredArg("player", "The player to send the message to", ArgTypes.PLAYER_REF)
    private val messageArgument = withRequiredArg("message", "The message to send", ArgTypes.STRING)
    
    override fun executeSync(context: CommandContext) {
        val receiver = playerArgument.get(context)
        val rawArguments = CommandUtil.stripCommandName(context.inputString)
        val msg = rawArguments.split(" ").drop(1).joinToString(" ")
        
        val sender = context.sender()
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
        
        replyManager.setLastMessagedPlayer(sender.uuid, receiver.uuid)
    }
}