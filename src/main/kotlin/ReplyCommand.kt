package com.github.iipanda

import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.CommandUtil
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.SoundUtil

class ReplyCommand(
    private val replyManager: ReplyManager,
    private val messageFormatter: MessageFormatter,
) : CommandBase("reply", "Reply to your last private message") {
    init { // String argument only allows a single string with no spaces, so we parse the value manually using the raw command.
        // We include the argument so that the presence of the parameter is validated by the parser, and so that it's
        // included in help messages.
        withListRequiredArg("message", "The message to send", ArgTypes.STRING)
        
        setAllowsExtraArguments(true)
    }
    
    override fun executeSync(context: CommandContext) {
        val sender = context.sender()
        
        val receiverUuid = replyManager.getLastMessagedPlayer(sender.uuid)
            ?: return context.sendMessage(Message.raw("You have no one to reply to").color("#ff0055"))
        
        val receiver = Universe.get().getPlayer(receiverUuid)
            ?: return context.sendMessage(Message.raw("The player you're replying to is offline").color("#ff0055"))
        
        val msg = CommandUtil.stripCommandName(context.inputString)
        val message = messageFormatter.formatPrivateMessage(sender, receiver, msg)
        
        sender.sendMessage(message)
        
        val soundEventIndex = SoundEvent.getAssetMap().getIndex("Message_Received")
        receiver.sendMessage(message)
        SoundUtil.playSoundEvent2dToPlayer(receiver, soundEventIndex, SoundCategory.UI)
    }
}
