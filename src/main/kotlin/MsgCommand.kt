package com.github.iipanda

import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.CommandUtil
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.universe.world.SoundUtil

class MsgCommand(
    private val replyManager: ReplyManager,
    private val messageFormatter: MessageFormatter,
) : CommandBase("msg", "Private message the player") {
    init {
        addAliases("pm", "tell", "whisper")
        setAllowsExtraArguments(true)
    }
    
    private val playerArgument = withRequiredArg("player", "The player to send the message to", ArgTypes.PLAYER_REF)
    
    // String argument only allows a single string with no spaces, so we parse the value manually using the raw command.
    // We include the argument so that the presence of the parameter is validated by the parser, and so that it's
    // included in help messages.
    @Suppress("Unused")
    private val messageArgument = withRequiredArg("message", "The message to send", ArgTypes.STRING)
    
    override fun executeSync(context: CommandContext) {
        val receiver = playerArgument.get(context)
        val rawArguments = CommandUtil.stripCommandName(context.inputString)
        val msg = rawArguments.split(" ").drop(1).joinToString(" ")
        
        val sender = context.sender()
        val messageToSender = messageFormatter.formatSentMessage(sender, receiver, msg)
        val messageToReceiver = messageFormatter.formatReceivedMessage(sender, receiver, msg)
        
        sender.sendMessage(messageToSender)
        
        val soundEventIndex = SoundEvent.getAssetMap().getIndex("Message_Received")
        receiver.sendMessage(messageToReceiver)
        SoundUtil.playSoundEvent2dToPlayer(receiver, soundEventIndex, SoundCategory.UI)
        
        replyManager.setLastMessagedPlayer(sender.uuid, receiver.uuid)
    }
}
