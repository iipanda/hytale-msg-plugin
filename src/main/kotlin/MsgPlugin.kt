package com.github.iipanda

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import java.nio.file.Files

@Suppress("unused")
class MsgPlugin(init: JavaPluginInit) : JavaPlugin(init) {
    private val replyManager = ReplyManager()
    private val config = withConfig(MsgPluginConfig.CODEC)
    private val messageFormatter = MessageFormatter(config)
    
    private fun info(msg: String) {
        logger.at(java.util.logging.Level.INFO).log(msg)
    }
    
    private fun ensureConfigFileExists() {
        val path = dataDirectory.resolve("config.json")
        if (Files.isRegularFile(path)) return
        config.save().join()
    }
    
    override fun setup() {
        info("Setting up msg plugin")
        ensureConfigFileExists()
        commandRegistry.registerCommand(MsgCommand(replyManager, messageFormatter))
        commandRegistry.registerCommand(ReplyCommand(replyManager, messageFormatter))
    }
}
