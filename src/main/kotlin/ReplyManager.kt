package com.github.iipanda

import java.util.*

class ReplyManager {
    private val lastMessagedPlayers = HashMap<UUID, UUID>()
    
    fun setLastMessagedPlayer(senderUuid: UUID, receiverUuid: UUID) {
        lastMessagedPlayers[senderUuid] = receiverUuid
        lastMessagedPlayers[receiverUuid] = senderUuid
    }
    
    fun getLastMessagedPlayer(senderUuid: UUID) = lastMessagedPlayers.get(senderUuid)
}