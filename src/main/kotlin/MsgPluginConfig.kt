package com.github.iipanda

import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.server.core.Message

class MsgPluginConfig {
    var messageFormatSent = Message.join(
        Message.raw("Me"),
        Message.raw(" » ").color("#9c9c9c"),
        Message.raw("{receiver}").color("#fca903"),
        Message.raw(": {message}"),
    )
    
    var messageFormatReceived = Message.join(
        Message.raw("{sender}").color("#fca903"),
        Message.raw(" » ").color("#9c9c9c"),
        Message.raw("Me"),
        Message.raw(": {message}"),
    )
    
    companion object {
        val CODEC: BuilderCodec<MsgPluginConfig> =
            BuilderCodec.builder(MsgPluginConfig::class.java) { MsgPluginConfig() }.append(
                KeyedCodec("MessageFormatSent", Message.CODEC),
                { cfg, value -> cfg.messageFormatSent = value },
                { cfg -> cfg.messageFormatSent },
            ).add().append(
                KeyedCodec("MessageFormatReceived", Message.CODEC),
                { cfg, value -> cfg.messageFormatReceived = value },
                { cfg -> cfg.messageFormatReceived },
            ).add().build()
    }
}
