package com.github.iipanda

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit

@Suppress("unused")
class MsgPlugin(init: JavaPluginInit) : JavaPlugin(init) {
    private val replyManager = ReplyManager()
    
    private fun info(msg: String) {
        logger.at(java.util.logging.Level.INFO).log(msg)
    }
    
    override fun setup() {
        info("Setting up msg plugin")
        commandRegistry.registerCommand(MsgCommand(replyManager))
        commandRegistry.registerCommand(ReplyCommand(replyManager))
    }
}