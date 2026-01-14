package com.github.iipanda

import com.hypixel.hytale.server.core.Message

fun buildMessage(senderName: String, receiverName: String, content: String) = Message.join(
    Message.raw("[$senderName -> $receiverName]").color("#02b511"), Message.raw(" "), Message.raw(content)
)