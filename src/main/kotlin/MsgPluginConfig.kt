package com.github.iipanda

import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.server.core.Message

class MsgPluginConfig {
    var messageFormat: Message = DEFAULT_MESSAGE_FORMAT
    
    companion object {
        val DEFAULT_MESSAGE_FORMAT: Message = Message.join(
            Message.raw("[{sender} -> {receiver}]").color("#02b511"),
            Message.raw(" "),
            Message.raw("{message}"),
        )
        
        val CODEC: BuilderCodec<MsgPluginConfig> =
            BuilderCodec.builder(MsgPluginConfig::class.java) { MsgPluginConfig() }.documentation(
                """
                Msg plugin configuration.
                
                Supported placeholders:
                - {sender} (display name)
                - {receiver} (username)
                - {message}
                """.trimIndent()
            ).append(
                KeyedCodec("MessageFormat", Message.CODEC),
                { cfg, value -> cfg.messageFormat = value ?: DEFAULT_MESSAGE_FORMAT },
                { cfg -> cfg.messageFormat },
            ).documentation("Format for both /msg and /reply output.").add().build()
    }
}
