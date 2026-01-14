package com.github.iipanda

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit

@Suppress("unused")
class HytalePlugin(init: JavaPluginInit) : JavaPlugin(init) {
    private fun info(msg: String) {
        logger.at(java.util.logging.Level.INFO).log(msg)
    }
    
    override fun setup() {
        info("Setting up plugin")
        commandRegistry.registerCommand(HelloCommand())
    }
    
    override fun start() {
        info("Starting plugin")
    }
    
    override fun shutdown() {
        info("Shutting down plugin")
    }
}